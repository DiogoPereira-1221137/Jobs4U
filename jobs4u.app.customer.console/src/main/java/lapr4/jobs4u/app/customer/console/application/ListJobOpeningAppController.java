/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lapr4.jobs4u.app.customer.console.application;

import lapr4.jobs4u.app.server.console.FollowUpServer;
import lapr4.jobs4u.notification.CustomMessage;
import lapr4.jobs4u.notification.MessageUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The type List all candidates controller.
 *
 * @author losa
 */
public class ListJobOpeningAppController {


    public CustomMessage fetchJobOpenings(DataInputStream input, DataOutputStream output) throws IOException {

        MessageUtil.writeMessage(output, FollowUpServer.REQ_JOBOP,new ArrayList<>());

        return MessageUtil.readMessage(input);

    }

    public CustomMessage howMuchJobApplications(DataInputStream input, DataOutputStream output, byte[] jobOpeningData) throws IOException {

        MessageUtil.writeMessage(output, FollowUpServer.REQ_HMUCHJOBAPP, new ArrayList<>(Collections.singleton(jobOpeningData)));

        return MessageUtil.readMessage(input);

    }

}
