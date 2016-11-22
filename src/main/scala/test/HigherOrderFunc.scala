package test

import akka.http.scaladsl.model.{HttpCharsets, HttpMethods, HttpProtocols}

/**
  * Created by root on 11/6/16.
  */
object HigherOrderFunc {
	def test(): Unit = {
		`hello:before`("abc") {
			hello
		}
	}

	def around (str: String) (s: (String) => Unit): Unit = {
		println("before")
		s(str)
		println("after")
	}

	def add_1 (i: Int): Int = {
		i + 1
	}

	def add_2 (i: Int): Int = {
		i + 2
	}

	def func (funcs: ((Int) => Int)*) (i: Int): Int = {
		var tmp = i
		val list = funcs.map { func =>
			func(tmp)
		}

		list.map { i =>
			tmp = tmp + i
		}
		tmp
	}

	def `hello:before` (str: String) (func: (String) => Unit): Unit = {
		println("before")
		if (str.equals("break"))
			return
		func(str)
	}

	def hello (str: String) = {
		println("hello " + str);
	}
}
