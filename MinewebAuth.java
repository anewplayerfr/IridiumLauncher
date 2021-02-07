package fr.anewplayerfr.launcher.template;

import java.io.IOException;

import fr.northenflo.auth.exception.AccountSuspendException;
import fr.northenflo.auth.exception.DataEmptyException;
import fr.northenflo.auth.exception.DataWrongException;
import fr.northenflo.auth.exception.RequireGAuthException;
import fr.northenflo.auth.exception.ServerNotFoundException;
import fr.northenflo.auth.mineweb.AuthMineweb;
import fr.northenflo.auth.mineweb.utils.TypeConnection;
import fr.trxyy.alternative.alternative_api.account.Session;
import fr.trxyy.alternative.alternative_api.utils.Logger;

public class MinewebAuth
{
	public boolean isAuthed = false;
	private static Session session = new Session();

	public MinewebAuth(String userName, String passWord, String url) throws RequireGAuthException,
			AccountSuspendException, DataWrongException, DataEmptyException, IOException, ServerNotFoundException {
		AuthMineweb.setTypeConnection(TypeConnection.launcher);
		AuthMineweb.setUrlRoot(url);
		AuthMineweb.setUsername(userName);
		AuthMineweb.setPassword(passWord);

		tryLogin();

	}

	public void tryLogin() throws RequireGAuthException, AccountSuspendException, DataWrongException,
			DataEmptyException, IOException, ServerNotFoundException {
		Logger.log("Try login... (Mineweb auth)");

		AuthMineweb.start();

		if (AuthMineweb.isConnected()) {
			session.setUsername(fr.northenflo.auth.mineweb.utils.Get.getSession.getUsername());
			session.setToken(fr.northenflo.auth.mineweb.utils.Get.getSession.getClientToken());
			session.setUuid(fr.northenflo.auth.mineweb.utils.Get.getSession.getUuid());

			this.isAuthed = true;
		}
	}

	public boolean isLogged() {
		return isAuthed;
	}

	public Session getSession() {
		return session;
	}

	public fr.northenflo.auth.mineweb.utils.Get.getSession minewebSession() {
		return AuthMineweb.getSession;
	}
}
