/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.okhttp.v3;

import com.navercorp.pinpoint.bootstrap.plugin.request.ClientRequestTrace;
import com.navercorp.pinpoint.common.plugin.util.HostAndPort;
import com.navercorp.pinpoint.common.util.StringUtils;
import com.navercorp.pinpoint.plugin.okhttp.EndPointUtils;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.net.URL;

/**
 * @author jaehong.kim
 */
public class OkHttpClientRequestTrace implements ClientRequestTrace {
    private final Request request;

    public OkHttpClientRequestTrace(final Request request) {
        this.request = request;
    }

    @Override
    public void setHeader(final String name, final String value) {
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public String getDestinationId() {
        final HttpUrl httpUrl = request.url();
        if (httpUrl != null) {
            URL url = httpUrl.url();
            if (url != null && url.getHost() != null) {
                final int port = EndPointUtils.getPort(url.getPort(), url.getDefaultPort());
                return HostAndPort.toHostAndPortString(url.getHost(), port);
            }
        }
        return "Unknown";
    }

    @Override
    public String getUrl() {
        final HttpUrl httpUrl = request.url();
        if (httpUrl != null) {
            return httpUrl.url().toString();
        }
        return null;
    }

    @Override
    public String getEntityValue() {
        return null;
    }

    @Override
    public String getCookieValue() {
        for (String cookie : request.headers("Cookie")) {
            if (StringUtils.hasLength(cookie)) {
                return cookie;
            }
        }
        return null;
    }
}
