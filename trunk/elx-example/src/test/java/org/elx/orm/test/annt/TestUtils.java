/*Copyright 2012 ELX

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package org.elx.orm.test.annt;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.junit.Test;

public class TestUtils {

	@Test
	public void testText() throws Exception {
		final java.util.Date date = new java.util.Date(
				System.currentTimeMillis());
		final Class<?> type = date.getClass();
		if (type.isAssignableFrom(java.sql.Date.class)) {
			System.out.println("SI");
		} else {
			System.out.println("NO");
		}

		// The 0 symbol shows a digit or 0 if no digit present
		NumberFormat formatter = new DecimalFormat("00000.0");
		String s = formatter.format(-1234.567); // -001235
		System.out.println("-1234.567->" + s);
		// notice that the number was rounded up

		// The # symbol shows a digit or nothing if no digit present
		formatter = new DecimalFormat("##");
		s = formatter.format(-1234.567); // -1235
		System.out.println("##->-1234.567->" + s);
		s = formatter.format(0); // 0
		System.out.println("##->0->" + s);
		formatter = new DecimalFormat("##00");
		s = formatter.format(0); // 00
		System.out.println("##00->0->" + s);
		formatter = new DecimalFormat("##.0");
		s = formatter.format(0); // 00
		System.out.println("##0.0->0->" + s);
		s = formatter.format(89.887); // 00
		System.out.println("##0.0->4489.887->" + s);

		// System.out.format("%5s",
		// Integer.toString(234678),Integer.toString(234678),Integer.toString(234678));

		final java.text.DecimalFormat nft = new java.text.DecimalFormat(
				"#00000000.###");
		nft.setDecimalSeparatorAlwaysShown(false);
		System.out.println(nft.format(20));

		final Class<String> clazzType = String.class;
		Object value = new Double(355.8799);
		formatter = new DecimalFormat("00D  ATPEPE DD 8.###");
		value = clazzType.cast(formatter.format(value)); // -001235
		System.out.println(value);

	}

}
