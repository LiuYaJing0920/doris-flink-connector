// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package org.apache.doris.flink.cfg;


import org.apache.flink.util.Preconditions;

import java.io.Serializable;
import java.util.Properties;

/**
 * Doris sink batch options.
 */
public class DorisExecutionOptions implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int DEFAULT_CHECK_INTERVAL = 10000;
    public static final int DEFAULT_MAX_RETRY_TIMES = 1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 1024;
    private static final int DEFAULT_BUFFER_COUNT = 3;
    //batch flush
    private static final int DEFAULT_FLUSH_QUEUE_SIZE = 2;
    private static final int DEFAULT_BUFFER_FLUSH_MAX_ROWS = 50000;
    private static final int DEFAULT_BUFFER_FLUSH_MAX_BYTES = 10 * 1024 * 1024;
    private static final long DEFAULT_BUFFER_FLUSH_INTERVAL_MS = 10 * 1000;
    private final int checkInterval;
    private final int maxRetries;
    private final int bufferSize;
    private final int bufferCount;
    private final String labelPrefix;
    /**
     * Properties for the StreamLoad.
     */
    private final Properties streamLoadProp;
    private final Boolean enableDelete;
    private final Boolean enable2PC;

    //batch mode param
    private int flushQueueSize;
    private int bufferFlushMaxRows;
    private int bufferFlushMaxBytes;
    private long bufferFlushIntervalMs;
    private boolean enableBatchMode;

    public DorisExecutionOptions(int checkInterval,
                                 int maxRetries,
                                 int bufferSize,
                                 int bufferCount,
                                 String labelPrefix,
                                 Properties streamLoadProp,
                                 Boolean enableDelete,
                                 Boolean enable2PC,
                                 boolean enableBatchMode,
                                 int flushQueueSize,
                                 int bufferFlushMaxRows,
                                 int bufferFlushMaxBytes,
                                 long bufferFlushIntervalMs) {
        Preconditions.checkArgument(maxRetries >= 0);
        this.checkInterval = checkInterval;
        this.maxRetries = maxRetries;
        this.bufferSize = bufferSize;
        this.bufferCount = bufferCount;
        this.labelPrefix = labelPrefix;
        this.streamLoadProp = streamLoadProp;
        this.enableDelete = enableDelete;
        this.enable2PC = enable2PC;

        this.enableBatchMode = enableBatchMode;
        this.flushQueueSize = flushQueueSize;
        this.bufferFlushMaxRows = bufferFlushMaxRows;
        this.bufferFlushMaxBytes = bufferFlushMaxBytes;
        this.bufferFlushIntervalMs = bufferFlushIntervalMs;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builderDefaults() {
        Properties properties = new Properties();
        properties.setProperty("format", "json");
        properties.setProperty("read_json_by_line", "true");
        return new Builder().setStreamLoadProp(properties);
    }
    
    public static DorisExecutionOptions defaults() {
        Properties properties = new Properties();
        properties.setProperty("format", "json");
        properties.setProperty("read_json_by_line", "true");
        return new Builder().setStreamLoadProp(properties).build();
    }

    public Integer checkInterval() {
        return checkInterval;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getBufferCount() {
        return bufferCount;
    }

    public String getLabelPrefix() {
        return labelPrefix;
    }

    public Properties getStreamLoadProp() {
        return streamLoadProp;
    }

    public Boolean getDeletable() {
        return enableDelete;
    }

    public Boolean enabled2PC() {
        return enable2PC;
    }

    public int getFlushQueueSize() {
        return flushQueueSize;
    }

    public int getBufferFlushMaxRows() {
        return bufferFlushMaxRows;
    }

    public int getBufferFlushMaxBytes() {
        return bufferFlushMaxBytes;
    }

    public long getBufferFlushIntervalMs() {
        return bufferFlushIntervalMs;
    }

    public boolean enableBatchMode() {
        return enableBatchMode;
    }

    /**
     * Builder of {@link DorisExecutionOptions}.
     */
    public static class Builder {
        private int checkInterval = DEFAULT_CHECK_INTERVAL;
        private int maxRetries = DEFAULT_MAX_RETRY_TIMES;
        private int bufferSize = DEFAULT_BUFFER_SIZE;
        private int bufferCount = DEFAULT_BUFFER_COUNT;
        private String labelPrefix = "";
        private Properties streamLoadProp = new Properties();
        private boolean enableDelete = true;
        private boolean enable2PC = true;

        private int flushQueueSize = DEFAULT_FLUSH_QUEUE_SIZE;
        private int bufferFlushMaxRows = DEFAULT_BUFFER_FLUSH_MAX_ROWS;
        private int bufferFlushMaxBytes = DEFAULT_BUFFER_FLUSH_MAX_BYTES;
        private long bufferFlushIntervalMs = DEFAULT_BUFFER_FLUSH_INTERVAL_MS;
        private boolean enableBatchMode = false;


        public Builder setCheckInterval(Integer checkInterval) {
            this.checkInterval = checkInterval;
            return this;
        }

        public Builder setMaxRetries(Integer maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        public Builder setBufferSize(int bufferSize) {
            this.bufferSize = bufferSize;
            return this;
        }

        public Builder setBufferCount(int bufferCount) {
            this.bufferCount = bufferCount;
            return this;
        }

        public Builder setLabelPrefix(String labelPrefix) {
            this.labelPrefix = labelPrefix;
            return this;
        }

        public Builder setStreamLoadProp(Properties streamLoadProp) {
            this.streamLoadProp = streamLoadProp;
            return this;
        }

        public Builder setDeletable(Boolean enableDelete) {
            this.enableDelete = enableDelete;
            return this;
        }

        public Builder disable2PC() {
            this.enable2PC = false;
            return this;
        }

        public Builder enableBatchMode() {
            this.enableBatchMode = true;
            return this;
        }

        public Builder setFlushQueueSize(int flushQueueSize) {
            this.flushQueueSize = flushQueueSize;
            return this;
        }

        public Builder setBufferFlushIntervalMs(long bufferFlushIntervalMs) {
            Preconditions.checkState(bufferFlushIntervalMs >= 1000, "bufferFlushIntervalMs must be greater than or equal to 1 second");
            this.bufferFlushIntervalMs = bufferFlushIntervalMs;
            return this;
        }

        public Builder setBufferFlushMaxRows(int bufferFlushMaxRows) {
            this.bufferFlushMaxRows = bufferFlushMaxRows;
            return this;
        }

        public Builder setBufferFlushMaxBytes(int bufferFlushMaxBytes) {
            this.bufferFlushMaxBytes = bufferFlushMaxBytes;
            return this;
        }

        public DorisExecutionOptions build() {
            return new DorisExecutionOptions(checkInterval, maxRetries, bufferSize, bufferCount, labelPrefix,
                    streamLoadProp, enableDelete, enable2PC, enableBatchMode, flushQueueSize, bufferFlushMaxRows, bufferFlushMaxBytes, bufferFlushIntervalMs);
        }
    }
}
