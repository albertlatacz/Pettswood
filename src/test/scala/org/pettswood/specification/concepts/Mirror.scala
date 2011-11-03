package org.pettswood.specification.concepts

import org.pettswood._

class Mirror extends Concept {
  var state = List.empty[String]

  def cell(text: String) = {
    state = text :: state
    state match {
      case List(input) => Pass(input)
      case List("becomes", _) => Setup()
      case List(expected, _, input) => Result.given(expected, input.reverse)
      case _ => Exception("Unhandled state: " + state)
    }
  }
}