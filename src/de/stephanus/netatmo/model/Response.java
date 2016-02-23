package de.stephanus.netatmo.model;

public class Response {

	private Body body;
	private String status;
	private double time_exec;
	private long time_server;
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getTime_exec() {
		return time_exec;
	}
	public void setTime_exec(double time_exec) {
		this.time_exec = time_exec;
	}
	public long getTime_server() {
		return time_server;
	}
	public void setTime_server(long time_server) {
		this.time_server = time_server;
	}
	
	
}
