// Copyright 2008 Fang Yidong
// https://github.com/fangyidong/json-simple

package ReadWrite.Json;

import java.io.IOException;
import java.io.Writer;

public interface JSONStreamAware {
    void writeJSONString(Writer var1) throws IOException;
}