package org.eclipse.gemoc.benchmark.property.monitor

import java.io.File
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.gemoc.benchmark.languages.BenchmarkLanguage
import org.eclipse.gemoc.benchmark.utils.PDETestResultsCollector
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter
import org.junit.runners.Parameterized.Parameters

import static org.eclipse.gemoc.benchmark.property.monitor.PropertyBenchmarkingData.*

@RunWith(Parameterized)
class BenchmarkTimeTestSuite {
	
	@Parameters
	static def Collection<Object[]> data() {
		return propertyBenchmarkingCases
	}
	
	@Parameter
	public var String monitoringCase
	
	@Parameter(1)
	public var BenchmarkLanguage language
	
	@Parameter(2)
	public var String modelName
	
	@Parameter(3)
	public var String initializationArguments
	
	@Parameter(4)
	public var String propertyName
	
	@Rule
	public TemporaryFolder tmpFolderCreator = new TemporaryFolder();

	var String outputFolderPath
	
	// Constants
	static val int port = 7777
	static val int minMemory = 1024
	static val int maxMemory = 5120
	static val String javaHome = "java" //"/usr/lib/jvm/java-8-oracle/bin/java"
	static val String wsPath = "file:/home/dorian/Téléchargements/gemoc-studio/workspace/runtime-LanguageWorkbench"
			//"file:/home/dorian/Téléchargements/gemoc-studio/workspace"
	static val String rWdPath = "/home/dorian/workspace/Rsle19"
	static val String outputRPath = "results"
	
	static val Map<String, Map<String, Set<String>>> caseNumber = newHashMap

	static def String prepareProperty(String key, String value) {
		return '''-D«key»=«value»'''
	}

	@Before
	def void prepareOutput() {
		// Create output folder
		val languageSimpleName = language.name.substring(language.name.lastIndexOf(".")+1)
		val modelSimpleName = modelName.substring(0,modelName.lastIndexOf("."))
		val outputFolder = new File(rWdPath + "/" + outputRPath + "/" + languageSimpleName + "_" + modelSimpleName)
		if (!outputFolder.exists)
			outputFolder.mkdir
		outputFolderPath = outputFolder.absolutePath
	}

	def void log(String s) {
		println("### [" + modelName + "_" + propertyName + "] " + s)
	}
	
	@Test
	def void test() {
		// These params are completely specific to an environment,
		// and they need the PDE test suite (there, BenchmarkSingleJVMTestSuite) to have
		// been executed at least one before via Eclispe, so that its conf files
		// are ready.
		// NOTE: requires passwordless sudo!
		
		caseNumber.computeIfAbsent(modelName, [newHashMap])
			.computeIfAbsent(propertyName, [newHashSet(initializationArguments)])
			.add(initializationArguments)
		val nb = caseNumber.get(modelName).get(propertyName).size - 1
		
		log ("Preparing tmp folder")
		val tmpWs = tmpFolderCreator.root

		val List<String> params = #[//"sudo", "ionice", "-c", "2", "-n", "0", "nice", "-19",
				javaHome, "-Xms" + minMemory + "m", "-Xmx" + maxMemory + "m",
				"-Declipse.pde.launch=true", "-Declipse.p2.data.area=@config.dir/p2", "-Dfile.encoding=UTF-8",
				prepareProperty(BenchmarkSingleJVMTestSuite::modelProperty, modelName),
				prepareProperty(BenchmarkSingleJVMTestSuite::initializationArgumentsProperty, initializationArguments),
				prepareProperty(BenchmarkSingleJVMTestSuite::caseNumberProperty, "" + nb),
				prepareProperty(BenchmarkSingleJVMTestSuite::temporalPropertyProperty, propertyName),
				prepareProperty(BenchmarkSingleJVMTestSuite::languageProperty, language.name),
				prepareProperty(BenchmarkSingleJVMTestSuite::monitoringCaseProperty, monitoringCase),
				prepareProperty(BenchmarkSingleJVMTestSuite::outputFolderProperty, outputFolderPath), "-classpath",
				"/home/dorian/Téléchargements/gemoc-studio-2/plugins/org.eclipse.equinox.launcher_1.5.0.v20180512-1130.jar",
				"org.eclipse.equinox.launcher.Main", "-os", "linux", "-ws", "gtk", "-arch", "x86_64", "-nl", "fr_FR",
				"-consoleLog", "-version", "3", "-port", port.toString, "-testLoaderClass",
				"org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader", "-loaderpluginname",
				"org.eclipse.jdt.junit4.runtime", "-classNames", BenchmarkSingleJVMTestSuite.name, "-application",
				"org.eclipse.pde.junit.runtime.uitestapplication", "-product org.eclipse.platform.ide",
				"-testApplication", "org.eclipse.ui.ide.workbench", "-data", tmpWs.absolutePath, "-configuration",
				wsPath + "/.metadata/.plugins/org.eclipse.pde.core/pde-junit/", "-dev",
				wsPath + "/.metadata/.plugins/org.eclipse.pde.core/pde-junit/dev.properties",
				"-os", "linux", "-ws", "gtk", "-arch", "x86_64", "-nl", "fr_FR", "-consoleLog", "-testpluginname",
				"org.eclipse.gemoc.benchmark.property.monitor"]
		
		val PDETestResultsCollector collector = new PDETestResultsCollector("listening for measure")
		
		// Start Junit listener in separate job
		log ("Start dummy junit listener")
		val junitListener = new Runnable() {
			override run() {
				collector.run(port);
			}
		}
		val junitListenerThread = new Thread(junitListener)
		junitListenerThread.start

		// Run actual test
		log ("Start test in dedicated JVM")
		val ProcessBuilder processBuilder = new ProcessBuilder(params)
		val Process process = processBuilder.start()
		process.waitFor()

		// Finish test listener
		log ("Kill dummy junit listener")
		junitListenerThread.stop
	}
}
