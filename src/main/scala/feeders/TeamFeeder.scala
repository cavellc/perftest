package feeders

object TeamFeeder {

  final val apiUrl = "https://api-test.cavellc.io"

  final val organizationName = "perftest"

  final val teams = Array(
    Map("teamName" -> "one",   "teamToken" -> "1fHSUREIOUImvok7busbz69r51m5c77LLZiYfBNCICDwmGDGZUbTkOIQ"),
    Map("teamName" -> "two",   "teamToken" -> "HiAShIIXpuboltlVshHDRId8g9usVlKQXFF3XA6KsDCysdiSXqhOagxN"),
    Map("teamName" -> "three", "teamToken" -> "lLUSBmixhh7KjCrZtbzVfu3kpcbuZAIXapSbmuRhQBXjgCPPv8PRUl17"),
    Map("teamName" -> "four",  "teamToken" -> "6fxccU9i8W2wzU8T3HF2FfRMURXFADOcElsKzpsxrcbOC5BbJbXK62dV"),
    Map("teamName" -> "five",  "teamToken" -> "4FImVy9aBHcr8NvKDIXvAMtudj70osuqjCNb5XUA4IIa3O7AOfKSTPBM"),
    Map("teamName" -> "six",   "teamToken" -> "ZUbtJxFgAqZiSAFzWXpliweYbrSUvZnWuOAdqjbnUHiWOG4zSbnUA9LB"),
    Map("teamName" -> "seven", "teamToken" -> "Sm6ZEP0hiE1vLFiLo8EOOa7Ygd9IbQwdHAl89Q6rgYnXTBrDaV0bpZey"),
    Map("teamName" -> "eight", "teamToken" -> "S7nzU2hgsa2fGEpNueKLATebcOdb6Lievy9UOL3nuqxEbRKfAlOKYn79"),
    Map("teamName" -> "nine",  "teamToken" -> "XiZi8DCLJXMx6rpHiu5sCcnG8NujuVGd73EAYA6Yy4v2Qejv38KCD2bV"),
    Map("teamName" -> "ten",   "teamToken" -> "s8JAKrXWGaIFOo6GJ7KrVMdTkml4d15p9peqcDlGVgfXNedz6YdJPcBx")
  )

  def teamUrl(teamName: String) = s"$apiUrl/organizations/$organizationName/teams/$teamName/metrics"

  def headers(token: String) = Map(
    "Content-Type" -> "application/json",
    "Accept" -> "application/json",
    "Authorization" -> s"Bearer $token"
  )
}
