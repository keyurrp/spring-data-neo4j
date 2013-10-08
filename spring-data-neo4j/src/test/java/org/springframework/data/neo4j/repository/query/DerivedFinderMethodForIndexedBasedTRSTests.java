/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.neo4j.repository.query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.NodeTypeRepresentationStrategy;
import org.springframework.data.neo4j.support.typerepresentation.IndexBasedNodeTypeRepresentationStrategy;
import org.springframework.test.context.CleanContextCacheTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Tests for the various finder method based scenarios
 * , specifically where the Index Type Representation Strategy is being used.
 *
 * @author Oliver Gierke & Nicki Watt
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({CleanContextCacheTestExecutionListener.class, DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class DerivedFinderMethodForIndexedBasedTRSTests extends AbstractDerivedFinderMethodTestBase {

    @Autowired
    NodeTypeRepresentationStrategy strategy;

    @Before
    public void setup() {
        super.setup();
        assertThat("The tests in this class should be configured to use the Label " +
                "based Type Representation Strategy, however it is not ... ",
                strategy, instanceOf(IndexBasedNodeTypeRepresentationStrategy.class));
    }

    @Test
    @Override
    public void testQueryWithEntityGraphId() throws Exception {
        this.trsSpecificExpectedQuery = "START `thing_owner`=node({0}) MATCH `thing`-[:`owner`]->`thing_owner` WHERE `thing`.__type__ IN ['org.springframework.data.neo4j.repository.query.AbstractDerivedFinderMethodTestBase$Thing'] ";
        this.trsSpecificExpectedParams = new Object[] { 123 };
        super.testQueryWithEntityGraphId();
    }

}