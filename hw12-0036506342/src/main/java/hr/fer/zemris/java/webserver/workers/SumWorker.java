package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		int a;
		int b;
		
		if(context.getParameter("a") != null) {
			try {
				a = Integer.parseInt(context.getParameter("a"));
			} catch(Exception e) {
				a = 1;
			}
		} else {
			a = 1;
		}
		
		if(context.getParameter("b") != null) {
			try {
				b = Integer.parseInt(context.getParameter("b"));
			} catch(Exception e) {
				b = 2;
			}
		} else {
			b = 2;
		}
		
		int r = a + b;
		
		context.setTemporaryParameter("varA", Integer.toString(a));
		context.setTemporaryParameter("varB", Integer.toString(b));
		context.setTemporaryParameter("zbroj", Integer.toString(r));
		if(r % 2 == 0)
			context.setTemporaryParameter("imgName", "cat.png");
		else
			context.setTemporaryParameter("imgName", "dog.png");
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}
	
	
	
}
