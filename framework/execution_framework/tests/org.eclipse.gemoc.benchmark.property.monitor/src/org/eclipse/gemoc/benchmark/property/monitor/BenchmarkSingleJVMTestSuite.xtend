package org.eclipse.gemoc.benchmark.property.monitor

import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.FileReader
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.gemoc.benchmark.cases.BenchmarkingCase
import org.eclipse.gemoc.benchmark.utils.CSVHelper
import org.eclipse.gemoc.benchmark.utils.EclipseTestUtil
import org.eclipse.gemoc.benchmark.utils.Util
import org.eclipse.gemoc.executionframework.property.model.property.EPLProperty
import org.junit.After
import org.junit.Test

import static org.eclipse.gemoc.benchmark.property.monitor.LanguageData.*
import static org.eclipse.gemoc.benchmark.utils.BenchmarkHelpers.*
import org.eclipse.core.resources.IFolder

class BenchmarkSingleJVMTestSuite {

	public static val String monitoringCaseProperty = "monitoringCaseProperty"
	public static val String modelProperty = "modelProperty"
	public static val String initializationArgumentsProperty = "initializationArgumentsProperty"
	public static val String caseNumberProperty = "caseNumberProperty"
	public static val String paramProperty = "paramProperty"
	public static val String languageProperty = "languageProperty"
	public static val String temporalPropertyProperty = "temporalPropertyProperty"
	public static val String outputFolderProperty = "outputFolderProperty"
	public static val String tmpExecutionTimeFileProperty = "tmpExecutionTimeFileProperty"

	public static val String errorString = "!!!!ERROR"
	
	def void log(String s) {
		println("### [single test case] " + s)
	}
	
	var BenchmarkingCase<?, ?, ?> benchmarkingCase

	private def execute(IProgressMonitor m) {
		// Create engine parameterized with inputs
		log("Preparing engine")
		System.gc
		Thread.sleep(3000)
		log("Running engine")
		benchmarkingCase.execute
	}
	
	val Map<String,FileOutputStream> streams = new HashMap
	val Map<String,PrintWriter> writers = new HashMap
	
	private def void addCSV(String filename, String folder) {
		val dir = new File(folder)
		dir.mkdirs
		val csv = new File(folder + "/" + filename)
		csv.createNewFile
		val csvStream = new FileOutputStream(csv)
		val csvWriter = new PrintWriter(csvStream, true)
		streams.put(filename, csvStream)
		writers.put(filename, csvWriter)
	}
	
	private def List<String> getResults(File file) {
		val FileReader fileReader = new FileReader(file)
		val BufferedReader bufferedReader = new BufferedReader(fileReader)
		val List<String> lines = new ArrayList<String>
		for (var String l = bufferedReader.readLine(); l !== null; l = bufferedReader.readLine()) {
			lines.add(l)
		}
		fileReader.close
		file.delete
		return lines
	}

	@Test
	def void test() {
		// Disable logs
		val emptyPrintStream = createEmptyPrintStream
		System.setOut(emptyPrintStream)
		System.setErr(emptyPrintStream)
		
		val modelName = System.getProperty(modelProperty)
		val initializationArguments = System.getProperty(initializationArgumentsProperty)
		val caseNumber = System.getProperty(caseNumberProperty)
		val monitoringCase = System.getProperty(monitoringCaseProperty)
		val languageName = System.getProperty(languageProperty)
		val propertyName = System.getProperty(temporalPropertyProperty)
		val outputFolder = System.getProperty(outputFolderProperty)
		
		val String executionCSVFilename = '''execution«modelName»_«propertyName»_«caseNumber».csv'''
		val String initializationCSVFilename = '''initialize«modelName»_«propertyName»_«caseNumber».csv'''
		
		System.setProperty(tmpExecutionTimeFileProperty,"")
		
		val csv = new CSVHelper
		
		addCSV(executionCSVFilename, outputFolder)
		addCSV(initializationCSVFilename, outputFolder)
		
		Util::cleanWorkspace
		
		val job = new Job("single test case") {

			override protected run(IProgressMonitor m) {

				try {
					// Create eclipse project in test WS
					val eclipseProject = ResourcesPlugin::getWorkspace().getRoot().getProject(projectName);
					if (eclipseProject.exists)
						eclipseProject.delete(true, m)
					eclipseProject.create(m)
					eclipseProject.open(m)

					// Copy all the models in the test WS
					// TODO copy single model
					val modelFolder = new File(modelFolderName)
					val modelFolderInWS = copyFolderInWS(modelFolder, eclipseProject, m)
					
					val modelFileInProject = modelFolderInWS.getFile(modelName)
					val modelURI = URI.createPlatformResourceURI(modelFileInProject.fullPath.toString, true)
					
					val propertyFolder = new File(propertyFolderName)
					val propertyFolderInWS = copyFolderInWS(propertyFolder, eclipseProject, m)
					
//					val property = loadProperty(propertyFolderInWS, propertyName)
					val property1 = loadProperty(propertyFolderInWS, "exists_p_after_q.property")
					val property2 = loadProperty(propertyFolderInWS, "exists_p_between_q_and_r_RIGHT.property")
					
					benchmarkingCase = switch (monitoringCase) {
//						case "K3Language": new K3PropertyBenchmarkingCase(modelURI, initializationArguments, languages.get(languageName), #{property})
						case "K3Language": new K3PropertyBenchmarkingCase(
							modelURI, initializationArguments, languages.get(languageName), #{property1, property2}
						)
					}
					
					log("Warming up.")
					for (var i = 0; i < 10; i++) {
						benchmarkingCase.initialize()
						execute(m)
						log("cleaning up")
						Util::cleanup("org.eclipse.gemoc.example.k3fsm.k3dsa")
						log("cleaned up")
					}
					
					for (var i = 0; i < 20; i++) {
						log("Starting measure "+i)
						val long initTime = benchmarkingCase.initialize()
						val tmpExecutionTimeFile = File.createTempFile("benchmarkExecution", "benchmark")
						System.setProperty(tmpExecutionTimeFileProperty,tmpExecutionTimeFile.absolutePath)
						execute(m)
						csv.totalExecutionTimes.add(Long.parseLong(getResults(tmpExecutionTimeFile).head))
						csv.initializationTimes.add(initTime)
						Util::cleanup("org.eclipse.gemoc.example.k3fsm.k3dsa")
					}
					
					writers.get(executionCSVFilename).println(csv.exportExecutionTimes)
					writers.get(initializationCSVFilename).println(csv.exportInitializationTimes)

					// Done 
					return Status.OK_STATUS

				} catch (Throwable t) {
					log("Exception caught.")
					t.printStackTrace
					val StringWriter sw = new StringWriter();
					t.printStackTrace(new PrintWriter(sw));
					val status = new Status(Status.ERROR, "trace single time test", "An error occured in the test case", t)
					return status
				}
			}
		}
		job.schedule
		EclipseTestUtil.waitForJobs

		if (job.result != Status.OK_STATUS) {
			throw job.result.exception
		}
	}

	@After
	def void closeCSV() {
		streams.values.forEach[v|v.close]
		writers.values.forEach[v|v.close]
	}

	static val resourceSet = new ResourceSetImpl
	
	static def loadProperty(IFolder propertyFolder, String propertyName) {
		val propertyFileInProject = propertyFolder.getFile(propertyName)
		return if (propertyFileInProject.exists) {
			val propertyURI = URI.createPlatformResourceURI(propertyFileInProject.fullPath.toString, true)
			val propertyResource = resourceSet.getResource(propertyURI, true)
			if (propertyResource !== null) {
				if (propertyResource.errors.size == 0 && !propertyResource.contents.empty) {
					val topElement = propertyResource.contents.get(0)
					if (topElement instanceof EPLProperty) {
						return topElement as EPLProperty
					} else {
						println("[ERROR] Root element is not a property: " + propertyURI.toString)
						return null
					}
				} else {
					println("[ERROR] Resource is empty or contains errors: " + propertyURI.toString)
					return null
				}
			} else {
				println("[ERROR] Property resource not found: " + propertyURI.toString)
				return null
			}
		} else {
			null
		}
	}
}
