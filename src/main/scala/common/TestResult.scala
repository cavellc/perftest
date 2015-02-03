package common

import java.io.File
import java.net.InetAddress

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.typesafe.config.Config

class TestResult(resultsPath: String, config: Config) {

  def listFiles(directory: File): Array[File] = {
    val children = directory.listFiles()
    children ++ children.filter(_.isDirectory).flatMap(listFiles)
  }

  val logFiles = listFiles(new File(resultsPath)).filter(f => f.isFile && f.getName.endsWith(".log"))

  if (logFiles.size == 1) {
    val logFile = logFiles.head

    val accessKey = config.getString("accessKey")
    val secretKey = config.getString("secretKey")
    val bucketName = config.getString("bucketName")
    val resultsDir = config.getString("resultsDir")
    val hostname = InetAddress.getLocalHost.getHostName

    val objectKey = s"$resultsDir/$hostname/simulation.log"

    println(s"Using key $accessKey / $secretKey to store $objectKey.")

    new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey)).putObject(bucketName, objectKey, logFiles.head)
  } else println(s"Unexpected number of log files: ${logFiles.size}")
}
