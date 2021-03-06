/**
 * Copyright (C) 2010 Osinka <http://osinka.ru>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.osinka.camel.beanstalk.processors;

import com.osinka.camel.beanstalk.BeanstalkEndpoint;
import com.surftools.BeanstalkClient.Client;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Message;
import org.apache.camel.NoSuchHeaderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KickCommand extends DefaultCommand {
    private final transient Logger log = LoggerFactory.getLogger(getClass());

    public KickCommand(BeanstalkEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void act(final Client client, final Exchange exchange) throws NoSuchHeaderException, InvalidPayloadException {
        final Integer jobs = exchange.getIn().getMandatoryBody(Integer.class);
        final int result = client.kick(jobs);
        if (log.isDebugEnabled())
            log.debug(String.format("Kick %d jobs. Kicked %d actually.", jobs, result));

        final Message answer = getAnswerMessage(exchange);
        answer.setBody(result, Integer.class);
    }
}
