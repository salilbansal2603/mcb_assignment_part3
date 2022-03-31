package dto;

public class ErrorStatus {
	private Integer statusCode;
	private String errorCode;
	private String error;

	public ErrorStatus(Integer statusCode, String errorCode, String error) {
		this.errorCode = errorCode;
		this.statusCode = statusCode;
		this.error = error;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
