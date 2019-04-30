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
import org.eclipse.gemoc.benchmark.cases.BenchmarkingCase
import org.eclipse.gemoc.benchmark.languages.BenchmarkLanguage
import org.eclipse.gemoc.benchmark.utils.CSVHelper
import org.eclipse.gemoc.benchmark.utils.EclipseTestUtil
import org.junit.After
import org.junit.Test

import static org.eclipse.gemoc.benchmark.property.monitor.LanguageData.*
import static org.eclipse.gemoc.benchmark.utils.BenchmarkHelpers.*

class BenchmarkSingleJVMTestSuite {

	public static val String modelProperty = "modelProperty"
	public static val String paramProperty = "paramProperty"
	public static val String languageProperty = "languageProperty"
	public static val String tracingCaseProperty = "tracingCaseProperty"
	public static val String outputFolderProperty = "outputFolderProperty"
	public static val String tmpAddFileProperty = "tmpAddFileProperty"
	public static val String tmpRestoreFileProperty = "tmpRestoreFileProperty"
	public static val String tmpExecuteFileProperty = "tmpExecuteFileProperty"

	public static val String errorString = "!!!!ERROR"

	var BenchmarkLanguage language
	var URI modelURI
	var BenchmarkingCase benchmarkingCase

	def void log(String s) {
		println("### [single test case] " + s)
	}

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
		val csv = new File(folder + "/" + filename)
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

		// Read properties
		val String model = System.getProperty(modelProperty)
		val String tracingCaseString = System.getProperty(tracingCaseProperty)
		val String languageName = System.getProperty(languageProperty)
		val String outputFolder = System.getProperty(outputFolderProperty)
		
		val String addCSVFilename = "add"+tracingCaseString+".csv"
		val String restoreCSVFilename = "restore"+tracingCaseString+".csv"
		val String executionCSVFilename = "execute"+tracingCaseString+".csv"
		val String initializationCSVFilename = "initialize"+tracingCaseString+".csv"
		
		System.setProperty(tmpAddFileProperty,"")
		System.setProperty(tmpRestoreFileProperty,"")
		System.setProperty(tmpExecuteFileProperty,"")
		
		val csv = new CSVHelper
		
		addCSV(addCSVFilename, outputFolder)
		addCSV(restoreCSVFilename, outputFolder)
		addCSV(executionCSVFilename, outputFolder)
		addCSV(initializationCSVFilename, outputFolder)

//		switch (tracingCaseString) {
//			case "Clone": benchmarkingCase = new CloneBasedTracingCase
//			case "Generic": benchmarkingCase = new GenericTracingCase
//			case "Generated": benchmarkingCase = new GeneratedTracingCase
//			case "None" : benchmarkingCase = new NoTracingCase
//		}
		
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

					// Create model URI
					val modelFileInProject = modelFolderInWS.getFile(model)
					modelURI = URI.createPlatformResourceURI(modelFileInProject.fullPath.toString, true)
					//TODO instantiate benchmarking case with modelUri
					
					log("Warming up.")
					for (var i = 0; i < 10; i++) {
						benchmarkingCase.initialize()
						execute(m)
					}
					
					for (var i = 0; i < 20; i++) {
						log("Starting measure "+i)
						val long initTime = benchmarkingCase.initialize()
						val tmpAddFile = File.createTempFile("benchmarkAdd", "benchmark")
						val tmpRestoreFile = File.createTempFile("benchmarkRestore", "benchmark")
						val tmpExecuteFile = File.createTempFile("benchmarkExecute", "benchmark")
						System.setProperty(tmpAddFileProperty,tmpAddFile.absolutePath)
						System.setProperty(tmpRestoreFileProperty,tmpRestoreFile.absolutePath)
						System.setProperty(tmpExecuteFileProperty,tmpExecuteFile.absolutePath)
						execute(m)
						val addResults = getResults(tmpAddFile)
						val restoreResults = getResults(tmpRestoreFile)
						csv.addStateExecutionTimes.add(addResults.map[s|Long.parseLong(s)])
						csv.restoreStateExecutionTimes.add(restoreResults.map[s|Long.parseLong(s)])
						csv.totalExecutionTimes.add(Long.parseLong(getResults(tmpExecuteFile).head))
						csv.initializationTimes.add(initTime)
					}
					
					writers.get(addCSVFilename).println(csv.exportAddStateExecutionTimes)
					writers.get(restoreCSVFilename).println(csv.exportRestoreStateExecutionTimes)
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
}
