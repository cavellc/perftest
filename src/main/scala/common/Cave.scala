package common

import java.util.UUID

import play.api.libs.functional.syntax._
import play.api.libs.json._
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

case class Alert(id: Option[String] = None,
                 description: String,
                 enabled: Boolean,
                 period: String,
                 condition: String,
                 routing: Map[String, String] = Map.empty)

case class Auth(token: String, expires: DateTime)

case class Member(user: User, role: Role)

case class Metric(name: String, tags: Map[String, String] = Map.empty, timestamp: Long, value: Double)

case class MetricData(time: DateTime, value: Double)

case class MetricDataBulk(metrics: Seq[MetricData])

case class MetricInfo(name: String, tags: Seq[String])

case class Organization(name: String, email: String, notificationUrl: String, tokens: Seq[Token])

case class Team(name: String, tokens: Seq[Token])

case class Token(id: String, description: String, value: String, created: DateTime)

case class User(firstName: String, lastName: String, email: String)

case class UserOrganization(name: String, role: Role)

case class UserTeam(name: String, role: Role)

sealed trait Aggregator

object Aggregator {

  val all = Seq(Count, Min, Max, Mean, Mode, Median, Sum, Stddev, P99, P999, P95, P90)
  private[this] val byName = all.map(x => x.toString -> x).toMap

  def apply(value: String): Aggregator = fromString(value).getOrElse(UNDEFINED(value))

  def fromString(value: String): scala.Option[Aggregator] = byName.get(value)

  case class UNDEFINED(override val toString: String) extends Aggregator

  case object Count extends Aggregator {
    override def toString = "count"
  }

  case object Min extends Aggregator {
    override def toString = "min"
  }

  case object Max extends Aggregator {
    override def toString = "max"
  }

  case object Mean extends Aggregator {
    override def toString = "mean"
  }

  case object Mode extends Aggregator {
    override def toString = "mode"
  }

  case object Median extends Aggregator {
    override def toString = "median"
  }

  case object Sum extends Aggregator {
    override def toString = "sum"
  }

  case object Stddev extends Aggregator {
    override def toString = "stddev"
  }

  case object P99 extends Aggregator {
    override def toString = "p99"
  }

  case object P999 extends Aggregator {
    override def toString = "p999"
  }

  case object P95 extends Aggregator {
    override def toString = "p95"
  }

  case object P90 extends Aggregator {
    override def toString = "p90"
  }
}

sealed trait Role

object Role {

  val all = Seq(Admin, Member, Viewer, Team)
  private[this] val byName = all.map(x => x.toString -> x).toMap

  def apply(value: String): Role = fromString(value).getOrElse(UNDEFINED(value))

  def fromString(value: String): scala.Option[Role] = byName.get(value)

  case class UNDEFINED(override val toString: String) extends Role

  case object Admin extends Role {
    override def toString = "admin"
  }

  case object Member extends Role {
    override def toString = "member"
  }

  case object Viewer extends Role {
    override def toString = "viewer"
  }

  case object Team extends Role {
    override def toString = "team"
  }
}

package object json {

  implicit val jsonReadsCaveLLCEnum_Aggregator = __.read[String].map(Aggregator.apply)
  implicit val jsonWritesCaveLLCEnum_Aggregator = new Writes[Aggregator] {
    def writes(x: Aggregator) = JsString(x.toString)
  }
  implicit val jsonReadsCaveLLCEnum_Role = __.read[String].map(Role.apply)
  implicit val jsonWritesCaveLLCEnum_Role = new Writes[Role] {
    def writes(x: Role) = JsString(x.toString)
  }
  private[common] implicit val jsonReadsUUID = __.read[String].map(UUID.fromString)
  private[common] implicit val jsonWritesUUID = new Writes[UUID] {
    def writes(x: java.util.UUID) = JsString(x.toString)
  }
  private[common] implicit val jsonReadsJodaDateTime = __.read[String].map { str =>
    ISODateTimeFormat.dateTimeParser.parseDateTime(str)
  }
  private[common] implicit val jsonWritesJodaDateTime = new Writes[DateTime] {
    def writes(x: DateTime) = {
      val str = ISODateTimeFormat.dateTime.print(x)
      JsString(str)
    }
  }

  implicit def jsonReadsCaveLLCAlert: Reads[Alert] =
    ((__ \ "id").readNullable[String] and
     (__ \ "description").read[String] and
     (__ \ "enabled").read[Boolean] and
     (__ \ "period").read[String] and
     (__ \ "condition").read[String] and
     (__ \ "routing").readNullable[Map[String, String]].map(_.getOrElse(Map.empty))
    )(Alert.apply _)

  implicit def jsonWritesCaveLLCAlert: Writes[Alert] =
    ((__ \ "id").write[scala.Option[String]] and
     (__ \ "description").write[String] and
     (__ \ "enabled").write[Boolean] and
     (__ \ "period").write[String] and
     (__ \ "condition").write[String] and
     (__ \ "routing").write[Map[String, String]]
    )(unlift(Alert.unapply))

  implicit def jsonReadsCaveLLCAuth: Reads[Auth] =
    ((__ \ "token").read[String] and
     (__ \ "expires").read[DateTime]
    )(Auth.apply _)

  implicit def jsonWritesCaveLLCAuth: Writes[Auth] =
    ((__ \ "token").write[String] and
     (__ \ "expires").write[DateTime]
    )(unlift(Auth.unapply))

  implicit def jsonReadsCaveLLCMember: Reads[Member] =
    ((__ \ "user").read[User] and
     (__ \ "role").read[Role]
    )(Member.apply _)

  implicit def jsonWritesCaveLLCMember: Writes[Member] =
    ((__ \ "user").write[User] and
     (__ \ "role").write[Role]
    )(unlift(Member.unapply))

  implicit def jsonReadsCaveLLCMetric: Reads[Metric] =
    ((__ \ "name").read[String] and
     (__ \ "tags").readNullable[Map[String, String]].map(_.getOrElse(Map.empty)) and
     (__ \ "timestamp").read[Long] and
     (__ \ "value").read[Double]
    )(Metric.apply _)

  implicit def jsonWritesCaveLLCMetric: Writes[Metric] =
    ((__ \ "name").write[String] and
     (__ \ "tags").write[Map[String, String]] and
     (__ \ "timestamp").write[Long] and
     (__ \ "value").write[Double]
    )(unlift(Metric.unapply))

  implicit def jsonReadsCaveLLCMetricData: Reads[MetricData] =
    ((__ \ "time").read[DateTime] and
     (__ \ "value").read[Double]
    )(MetricData.apply _)

  implicit def jsonWritesCaveLLCMetricData: Writes[MetricData] =
    ((__ \ "time").write[DateTime] and
     (__ \ "value").write[Double]
    )(unlift(MetricData.unapply))

  implicit def jsonReadsCaveLLCMetricDataBulk: Reads[MetricDataBulk] =
    (__ \ "metrics").readNullable[Seq[MetricData]].map(_.getOrElse(Nil)).map { x => new MetricDataBulk(metrics = x)}

  implicit def jsonWritesCaveLLCMetricDataBulk: Writes[MetricDataBulk] = new Writes[MetricDataBulk] {
    def writes(x: MetricDataBulk) = Json.obj(
      "metrics" -> Json.toJson(x.metrics)
    )
  }

  implicit def jsonReadsCaveLLCMetricInfo: Reads[MetricInfo] =
    ((__ \ "name").read[String] and
     (__ \ "tags").readNullable[Seq[String]].map(_.getOrElse(Nil))
    )(MetricInfo.apply _)

  implicit def jsonWritesCaveLLCMetricInfo: Writes[MetricInfo] =
    ((__ \ "name").write[String] and
     (__ \ "tags").write[Seq[String]]
    )(unlift(MetricInfo.unapply))

  implicit def jsonReadsCaveLLCOrganization: Reads[Organization] =
    ((__ \ "name").read[String] and
     (__ \ "email").read[String] and
     (__ \ "notification_url").read[String] and
     (__ \ "tokens").readNullable[Seq[Token]].map(_.getOrElse(Nil))
    )(Organization.apply _)

  implicit def jsonWritesCaveLLCOrganization: Writes[Organization] =
    ((__ \ "name").write[String] and
     (__ \ "email").write[String] and
     (__ \ "notification_url").write[String] and
     (__ \ "tokens").write[Seq[Token]]
    )(unlift(Organization.unapply))

  implicit def jsonReadsCaveLLCTeam: Reads[Team] =
    ((__ \ "name").read[String] and
     (__ \ "tokens").readNullable[Seq[Token]].map(_.getOrElse(Nil))
    )(Team.apply _)

  implicit def jsonWritesCaveLLCTeam: Writes[Team] =
    ((__ \ "name").write[String] and
     (__ \ "tokens").write[Seq[Token]]
    )(unlift(Team.unapply))

  implicit def jsonReadsCaveLLCToken: Reads[Token] =
    ((__ \ "id").read[String] and
     (__ \ "description").read[String] and
     (__ \ "value").read[String] and
     (__ \ "created").read[DateTime]
    )(Token.apply _)

  implicit def jsonWritesCaveLLCToken: Writes[Token] =
    ((__ \ "id").write[String] and
     (__ \ "description").write[String] and
     (__ \ "value").write[String] and
     (__ \ "created").write[DateTime]
    )(unlift(Token.unapply))

  implicit def jsonReadsCaveLLCUser: Reads[User] =
    ((__ \ "first_name").read[String] and
     (__ \ "last_name").read[String] and
     (__ \ "email").read[String]
    )(User.apply _)

  implicit def jsonWritesCaveLLCUser: Writes[User] =
    ((__ \ "first_name").write[String] and
     (__ \ "last_name").write[String] and
     (__ \ "email").write[String]
    )(unlift(User.unapply))

  implicit def jsonReadsCaveLLCUserOrganization: Reads[UserOrganization] =
    ((__ \ "name").read[String] and
     (__ \ "role").read[Role]
    )(UserOrganization.apply _)

  implicit def jsonWritesCaveLLCUserOrganization: Writes[UserOrganization] =
    ((__ \ "name").write[String] and
     (__ \ "role").write[Role]
    )(unlift(UserOrganization.unapply))

  implicit def jsonReadsCaveLLCUserTeam: Reads[UserTeam] =
    ((__ \ "name").read[String] and
     (__ \ "role").read[Role]
    )(UserTeam.apply _)

  implicit def jsonWritesCaveLLCUserTeam: Writes[UserTeam] =
    ((__ \ "name").write[String] and
     (__ \ "role").write[Role]
    )(unlift(UserTeam.unapply))
}