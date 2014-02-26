package arimitsu.sf.test.undertowspring

import io.undertow.Undertow
import io.undertow.server.{HttpServerExchange, HttpHandler}
import org.springframework.stereotype.{Controller, Component}
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.web.bind.annotation.RequestMapping
import java.lang.reflect.Method

/**
 * User: sxend
 * Date: 14/02/26
 * Time: 22:53
 */
object Bootstrap {

  def main(args: Array[String]): Unit = {
    val appContext = new AnnotationConfigApplicationContext()
    appContext.scan("arimitsu.sf")
    appContext.refresh()
    Undertow.builder
      .addHttpListener(9000, "0.0.0.0")
      .setHandler(new HttpHandler {
      def handleRequest(exchange: HttpServerExchange) = {
        appContext.getBean(classOf[Router]).handle(exchange)
      }
    }).build.start()
  }
}

@Component
class Router {
  @Autowired
  private val appContext: ApplicationContext = null

  private lazy val mapping = appContext.getBeanNamesForAnnotation(classOf[Controller]).map {
    name => {
      val mapping = appContext.findAnnotationOnBean(name, classOf[RequestMapping])
      val req = Request(mapping.method()(0).name(), mapping.value()(0))
      val bean = appContext.getBean(name)
      val method = bean.getClass.getMethod("index", classOf[HttpServerExchange]) // 一旦index固定
      Mapping(req, bean, method)
    }
  }

  def handle(exchange: HttpServerExchange): Unit = {
    val request = Request(exchange.getRequestMethod.toString, exchange.getRequestPath)

    mapping.find {
      map => {
        request match {
          case map.request => true
          case _ => false
        }
      }
    } match {
      case Some(map) => {
        map.method.invoke(map.bean, exchange)
      }
      case None => exchange.getResponseSender.send("NotFound.")
    }
  }
}

case class Request(method: String, path: String)

case class Mapping(request: Request, bean: AnyRef, method: Method)
