/*
 * Copyright (C) 2015 Square, Inc.
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
package keywhiz.auth.cookie;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.security.SecureRandom;
import java.util.Base64;
import keywhiz.auth.xsrf.Xsrf;

import static com.google.common.base.Preconditions.checkNotNull;

public class CookieModule extends AbstractModule {
  private final String cookieKey;

  public CookieModule(String cookieKey) {
    this.cookieKey = checkNotNull(cookieKey);
  }

  @Override
  protected void configure() {
    bindConstant().annotatedWith(Xsrf.class).to("X-XSRF-TOKEN");
  }

  @Provides GCMEncryptor gcmEncryptor(SecureRandom secureRandom) {
    byte[] cookieBytes = Base64.getDecoder().decode(cookieKey);
    return new GCMEncryptor(cookieBytes, secureRandom);
  }
}
