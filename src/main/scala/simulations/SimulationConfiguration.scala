package simulations

import java.io.InputStreamReader

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.typesafe.config.ConfigFactory

case class SimulationConfiguration(repeats: Int, totalUsers: Int, atOnceUsers: Int, separatedBySeconds: Int)

object SimulationConfiguration {

  def init(fileName: String): Option[SimulationConfiguration] = {
    (sys.props.get("accessKey"), sys.props.get("secretKey"), sys.props.get("bucketName")) match {
      case (Some(accessKey), Some(secretKey), Some(bucketName)) =>
        readConfig(accessKey, secretKey, bucketName, fileName)

      case _ => None
    }
  }

  private[this] def readConfig(accessKey: String, secretKey: String, bucketName: String, fileName: String): Option[SimulationConfiguration] = {
    try {
      println(s"Fetching $fileName from $bucketName using $accessKey/$secretKey...")
      val config = ConfigFactory.parseReader(
        new InputStreamReader(
          new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey))
            .getObject(bucketName, fileName)
            .getObjectContent
        )
      )

      println("Done.")

      Some(SimulationConfiguration(
        config.getInt("repeats"),
        config.getInt("total-users"),
        config.getInt("at-once-users"),
        config.getInt("separated-by-seconds")))
    } catch {
      case _: Throwable =>
        None
    }
  }
}