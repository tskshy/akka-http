package api

/**
 * Created by nero on 16/5/31.
 */
object ActorProvider {
	val system = akka.actor.ActorSystem("http-server")
}
