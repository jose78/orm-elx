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
package org.elx.orm.db;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Jose Clavero Anderica jose.clavero.anderica@gmail.com
 * 
 */
public class Result {

	private Map<String, Integer> result = null;

	/**
 * 
 */
	public Result() {
		result = new TreeMap<String, Integer>();
	}

	/**
	 * 
	 * @param key
	 * @param value
	 */
	protected void addResult(String key, Integer value) {
		result.put(key, value);
	}

	/**
	 * @return the result
	 */
	public Map<String, Integer> getResult() {
		return result;
	}

}
