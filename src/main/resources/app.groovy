import com.example.spring.app.*

beans {
    simplePrinter(SimplePrinter)
    simpleBean(SimpleBean) { bean ->
        name = "goodBean"
        printer = simplePrinter
        bean.initMethod = 'init'
    }
}