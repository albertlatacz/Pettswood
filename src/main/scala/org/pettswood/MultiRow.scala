package org.pettswood


trait MultiRow extends SimpleConcept {
  
  def columns: String => String => Probe
  def initialiseRow() {}

  var rowPointer = 0;
  var currentProbes, probeTemplate = List.empty[(String) => Probe]

  override def row() {
    rowPointer += 1
    currentProbes = probeTemplate.reverse
    initialiseRow()
  }

  def probeFor(text: String): (String) => Probe = {
    try{
      columns(text)
    } catch {
      case e: MatchError => throw new RuntimeException("Unrecognised column probe: " + text)
      case e: NullPointerException => throw new RuntimeException("No column probes defined")
    }
  }

  def cell(cellText: String) = rowPointer match {
    case 1 => Setup()
    case 2 => probeTemplate = probeFor(cellText) :: probeTemplate; Setup()
    case x => probe(cellText)
  }

  def probe(cellText: String): Result = {
    val probe = currentProbes.head(cellText)
    currentProbes = currentProbes.tail
    resultFor(probe, cellText)
  }
}