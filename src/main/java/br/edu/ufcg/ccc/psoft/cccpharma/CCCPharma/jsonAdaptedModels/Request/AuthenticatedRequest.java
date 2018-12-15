package br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.Request;

import br.edu.ufcg.ccc.psoft.cccpharma.CCCPharma.jsonAdaptedModels.user.VerificationInformationUser;

public class AuthenticatedRequest<T> {
	private VerificationInformationUser user;
	private T data;
	
	public AuthenticatedRequest(VerificationInformationUser _user, T _data) {
		this.user = _user;
		this.data = _data;
	}
	
	public VerificationInformationUser getUser() {
		return user;
	}
	
	public void setUser(VerificationInformationUser user) {
		this.user = user;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}
}
