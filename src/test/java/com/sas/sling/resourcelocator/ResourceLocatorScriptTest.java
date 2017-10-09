/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sas.sling.resourcelocator;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.testing.mock.sling.junit.SlingContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.sas.sling.resource.ResourceLocator;
import com.sas.sling.resource.parser.ParseException;
import com.sas.sling.resource.parser.TokenMgrError;

public class ResourceLocatorScriptTest {

	@Rule
	public final SlingContext context = new SlingContext();
	
	private static String START_PATH = "/content/sample/en";
	private Date midPoint;
	
	private static String DATE_STRING = "Thu Aug 07 2013 16:32:59 GMT+0200";
	private static String NEW_DATE = "2013-08-08T16:32:59.000+02:00";
	private static String DATE_FORMAT = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z";

	@Before
	public void setUp() throws ParseException, java.text.ParseException {
		context.load().json("/data.json", "/content/sample/en");
		midPoint = new SimpleDateFormat(DATE_FORMAT).parse(DATE_STRING);
	}

	
	@Test
	public void testPropertyEquals() throws ParseException {
		String query = "[jcr:content/jcr:title] == 'English'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(4, found.size());
	}
	
	@Test
	public void testPropertyIs() throws ParseException {
		String query = "[jcr:content/jcr:title] is 'English'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(4, found.size());
	}
	
	@Test
	public void testDateBeforeValue() throws ParseException {
		String query = "[jcr:content/created] < date('2013-08-08T16:32:59.000+02:00')";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(3, found.size());
	}
	
	@Test
	public void testDateBeforeValue2() throws ParseException {
		String query = "[jcr:content/created] less than date('2013-08-08T16:32:59.000+02:00')";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(3, found.size());
	}
	
	@Test
	public void testDateBeforeValue3() throws ParseException {
		String query = "[jcr:content/created] < date('2013-08-08','yyyy-MM-dd')";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(3, found.size());
	}
	
	
	@Test
	public void testDateAndProperty() throws ParseException {
		String query = "[jcr:content/created] < date('2013-08-08T16:32:59.000+02:00') and [jcr:content/jcr:title] == 'English'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(3, found.size());
	}
	@Test
	public void testDateAndPropertyTwice() throws ParseException {
		String query = "([jcr:content/created] < date('2013-08-08T16:32:59.000+02:00') and [jcr:content/jcr:title] == 'English') or [jcr:content/jcr:title] == 'Mongolian'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(4, found.size());
	}
	
	@Test
	public void testDateOrProperty() throws ParseException {
		String query = "[jcr:content/created] < date('2013-08-08T16:32:59.000+02:00') or [jcr:content/jcr:title] == 'Mongolian'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(4, found.size());
	}
	
	@Test
	public void testDateAsString() throws ParseException {
		String query = "[jcr:content/created] < '2013-08-08T16:32'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(3, found.size());
	}
	
	@Test
	public void testNullPropertyAndLimit() throws ParseException {
		String query = "[jcr:content/foo] == null ";
		Resource resource = context.resourceResolver().getResource(START_PATH);
		List<Resource> found = ResourceLocator.startFrom(resource).limit(3).locateResources(query);
		assertEquals(3, found.size());
	}
	
	@Test
	public void testNullProperty() throws ParseException {
		String query = "[jcr:content/foo] == null ";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(20, found.size());
	}
	
	@Test
	public void testNumberLiteral() throws ParseException {
		String query = "[count] < 2 ";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testNumberLiteral2() throws ParseException {
		String query = "[count] < 2 or [count] > 1";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testNumberLiteral3() throws ParseException {
		String query = "[views] < 7 ";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testNotNullProperty() throws ParseException {
		String query = "[layout] != null ";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(5, found.size());
	}
	
	@Test 
	public void testNameFunctionIs() throws ParseException {
		String query = "name() == 'testpage1'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test 
	public void testNameFunctionAgainstRegex() throws ParseException {
		String query = "name() like 'testpage.*'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(4, found.size());
	}
	
	@Test 
	public void testNameFunctionAgainstRegex2() throws ParseException {
		String query = "name() like 'testpage[1-2]'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(2, found.size());
	}
	
	@Test
	public void testChildExistence() throws ParseException {
		String query = "name() == 'testpage3' ";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testBoolean() throws ParseException {
		String query = "[published] == true";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testContains() throws ParseException {
		String query = "[jcr:content/monkey] contains 'fish'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testContainsNot() throws ParseException {
		String query = "[jcr:content/monkey] contains not 'fish'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(19, found.size());
	}
	
	@Test
	public void testIn() throws ParseException {
		String query = "'fish' in [jcr:content/monkey]";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testPathLike() throws ParseException {
		String query = "path() like '/content/sample/en/testpage1.*'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(4, found.size());
	}
	
	@Test
	public void testPathLike2() throws ParseException {
		String query = "path() like '/content/sample/en/testpage1'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testPathLike3() throws ParseException {
		String query = "path() is '/content/sample/en/testpage1'";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(1, found.size());
	}
	
	@Test
	public void testNotIn() throws ParseException {
		String query = "'fish' not in [jcr:content/monkey]";
		List<Resource> found = handle(START_PATH, query);
		assertEquals(19, found.size());
	}
	
	@Test
	public void testInNotException() throws ParseException {
		TokenMgrError error = null;
		try {
			String query = "'fish' in not [jcr:content/monkey]";
			handle(START_PATH, query);
		} catch (TokenMgrError e) {
			error = e;
		}
		assertEquals("' in not' is not a valid comparison", error.getMessage());
	}
	
	private List<Resource> handle(String path, String filter) throws ParseException {
		Resource resource = context.resourceResolver().getResource(path);
		return ResourceLocator.startFrom(resource).locateResources(filter);
	}
}
