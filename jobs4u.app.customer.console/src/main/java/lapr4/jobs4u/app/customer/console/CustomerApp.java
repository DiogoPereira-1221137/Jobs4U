/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package lapr4.jobs4u.app.customer.console;

import lapr4.jobs4u.app.customer.console.presentation.FrontMenu;
import lapr4.jobs4u.infrastructure.persistence.PersistenceContext;
import lapr4.jobs4u.usermanagement.domain.PasswordGenerator;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.PlainTextEncoder;

import java.io.IOException;
import java.net.Socket;

/**
 * candidate App.
 */
@SuppressWarnings("squid:S106")
public final class CustomerApp {

	/**
	 * Empty constructor is private to avoid instantiation of this class.
	 */
	private CustomerApp() throws IOException {

	}

	public static void main(final String[] args) throws IOException {
		//        try {
		Socket socket = new Socket("localhost", 12345);
		AuthzRegistry.configure(PersistenceContext.repositories().users(), new PasswordGenerator(),
				new PlainTextEncoder());
//        } catch (Exception e) {
//			e.printStackTrace();
//	}
		System.out.println("=====================================");
		System.out.println("Customer App");
		System.out.println("=====================================");

		new FrontMenu(socket).mainLoop();

		// exiting the application, closing all threads
		System.exit(0);
	}
}
