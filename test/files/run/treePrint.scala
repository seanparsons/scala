/** Testing compact tree printers.
 */
object Test {
  import scala.tools.nsc._
  import interpreter._
  import java.io.{ OutputStream, BufferedReader, StringReader, PrintWriter, Writer, OutputStreamWriter}

  val code = """
    def foo = {
      var q: Boolean = false
      val x = if (true) {
        if (true) {
          if (true) {
            5
          }
          else if (true) {
            5
          } else {
            10
          }
        }
        else 20
      }
      else 30

      (x == 5) || !q || true
    }
  """

  class NullOutputStream extends OutputStream { def write(b: Int) { } }

  def main(args: Array[String]) {
    val settings = new Settings
    settings.classpath.value = System.getProperty("java.class.path")
    settings.Ycompacttrees.value = true

    val repl = new Interpreter(settings, new PrintWriter(new NullOutputStream))
    val power = new Power(repl)
    repl.interpret("""def initialize = "Have to interpret something or we get errors." """)
    println(power mkTree code)
  }
}
