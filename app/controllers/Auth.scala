package controllers

import play.api.mvc._
import models.User
import play.api.data.Form
import play.api.data.Forms.{mapping, ignored, nonEmptyText, tuple}
import play.api.i18n.Messages
import play.api.data.format.Formats._

object Auth extends Controller{
	val loginForm = Form(
		tuple(
			"email" -> nonEmptyText,
			"password" -> nonEmptyText
		) verifying (Messages("login.errors"), result => result match{
			case (email, password) => User.authenticate(email, password).isDefined
		})
	)

	val signupForm = Form(
		tuple(
			"email" -> nonEmptyText,
			"password" -> nonEmptyText,
			"password_conform" -> nonEmptyText
		) verifying (Messages("signup.password_not_match"), result => result match{
			case (email, pw1, pw2) => pw1 == pw2
		}) verifying (Messages("signup.already_sign_up"), result => result match{
			case (e, p1, p2) => ! User.findByEmail(e).isDefined
		})
	)

	// def check(username: String, password: String) = {
	// 	User.findByEmail(username) match{
	// 		case Some(u) => u.checkPassword(password)
	// 		case None => false
	// 	}
	// }

	def login = Action { implicit request => 
		Ok(views.html.login(loginForm))
	}

	def signup = Action { implicit request => 
		Ok(views.html.signup(signupForm))
	}

	def createNewUser = Action{ implicit request => 
		signupForm.bindFromRequest.fold(
			hasErrors = { formWithErrors => 
				BadRequest(views.html.signup(formWithErrors))
			},

			success = { userData => {
					User.insert(User(0, userData._1, userData._2))
					Redirect(routes.Auth.login).withNewSession
				}
			}
		)
	}

	def authenticate = Action { implicit request =>
		loginForm.bindFromRequest.fold(
			hasErrors = { form =>
				Redirect(routes.Auth.login).
					flashing(("error" -> Messages("login.errors")))
			},

			success = { user =>
				Redirect(routes.HomeControl.index()).withSession(Security.username -> user._1)
			}
		)
	}

	def logout = Action {
		Redirect(routes.Auth.login).withNewSession.flashing(
			"Success" -> "You are now logged out."
		)
	}
}

trait Secured {
	def username2(request: RequestHeader) = request.session.get(Security.username)
	
	implicit def username(implicit request: RequestHeader) = request.session.get(Security.username)


	def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.Auth.login)

	def IsAuthenticated(f: => String => Request[AnyContent] => Result) = {
		Security.Authenticated(username2, onUnauthorized) {
			user => Action(request => f(user)(request))
		}
	}

	def withUser(f: User => Request[AnyContent] => Result) = IsAuthenticated {
		username => implicit request => 
		User.findByEmail(username).map{ user => 
			f(user)(request)
		}.getOrElse(onUnauthorized(request))
	}
}