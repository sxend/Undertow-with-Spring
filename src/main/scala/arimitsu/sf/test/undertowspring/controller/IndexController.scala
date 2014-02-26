package arimitsu.sf.test.undertowspring.controller

import org.springframework.stereotype.Controller
import io.undertow.server.HttpServerExchange
import arimitsu.sf.test.undertowspring.service.ExampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMethod, RequestMapping}

/**
 * User: sxend
 * Date: 14/02/26
 * Time: 22:55
 */
@Controller
@RequestMapping(method = Array(RequestMethod.GET), value = Array("/"))
class IndexController {
  @Autowired
  private val exampleService: ExampleService = null

  def index(exchange: HttpServerExchange): Unit = {
    exchange.getResponseSender.send(exampleService.hello("World"))
  }
}
