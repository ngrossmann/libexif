import sbt._
import Keys._

object LibExifBuild extends Build {
  val dependencies = Seq(
      "org.scalatest" %% "scalatest" % "2.1.7" % "test" cross CrossVersion.binary
  )

  override lazy val settings = super.settings ++ Seq(
      libraryDependencies ++= dependencies,
      organization := "net.n12n.exif",
      version in ThisBuild := "0.2.0-SNAPSHOT",
      scalaVersion in ThisBuild := "2.11.4",
      scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature"),
      scalacOptions in doc ++= Seq("-diagrams", "-doc-title Scala Exif Library", "-implicits"),
      testOptions ++= Seq(Tests.Argument("-oSDW"))
  )

  lazy val slibexifSettings = Defaults.coreDefaultSettings ++ Seq(

    name := "slibexif",
    crossScalaVersions := Seq("2.10.4", "2.11.4"),
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    credentials += Credentials(Path.userHome / ".sbt" / "sonatype"),
    pomExtra := <licenses>
      <license>
        <name>GNU LESSER GENERAL PUBLIC LICENSE, Version 3</name>
        <url>http://www.gnu.org/licenses/lgpl.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
      <scm>
        <url>https://github.com/ngrossmann/slibexif</url>
      </scm>
  )

  lazy val slibexif = Project(id = "slibexif", base = file("."), settings = slibexifSettings)
  
  lazy val examples = Project(id = "examples", base = file("examples")).dependsOn(slibexif)
}
