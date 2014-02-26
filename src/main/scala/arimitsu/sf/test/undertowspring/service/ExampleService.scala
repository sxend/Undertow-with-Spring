package arimitsu.sf.test.undertowspring.service

import org.springframework.stereotype.Component

/**
 * User: sxend
 * Date: 14/02/26
 * Time: 22:55
 */
@Component
class ExampleService {
  def hello(value: String):String = {
    "Hello, " + value + "."
  }
}
