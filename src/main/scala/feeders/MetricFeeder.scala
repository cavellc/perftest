package feeders

object MetricFeeder {

  final val metricDefinitions = Array(
    Map("metricName" -> "orders", "metricTags" -> Map("shipTo" -> "US")),
    Map("metricName" -> "orders", "metricTags" -> Map("shipTo" -> "Intl")),
    Map("metricName" -> "response-time", "metricTags" -> Map("svc" -> "svc-important", "host" -> "svc1.local", "env" -> "production")),
    Map("metricName" -> "response-time", "metricTags" -> Map("svc" -> "svc-important", "host" -> "svc2.local", "env" -> "production")),
    Map("metricName" -> "response-time", "metricTags" -> Map("svc" -> "svc-important", "host" -> "svc3.local", "env" -> "production")),
    Map("metricName" -> "response-time", "metricTags" -> Map("svc" -> "svc-critical", "host" -> "svc1.local", "env" -> "production")),
    Map("metricName" -> "response-time", "metricTags" -> Map("svc" -> "svc-critical", "host" -> "svc2.local", "env" -> "production")),
    Map("metricName" -> "response-time", "metricTags" -> Map("svc" -> "svc-critical", "host" -> "svc3.local", "env" -> "production")),
    Map("metricName" -> "response-time", "metricTags" -> Map("svc" -> "svc-important", "host" -> "svc3.local", "env" -> "test")),
    Map("metricName" -> "response-time", "metricTags" -> Map("svc" -> "svc-critical", "host" -> "svc3.local", "env" -> "test"))
  )
}
