/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.    See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
 * @test
 * @bug 8151939
 * @summary os+cpu output should contain some os,cpu information
 * @library /testlibrary
 * @modules java.base/sun.misc
 *          java.management
 * @build jdk.test.lib.OutputAnalyzer jdk.test.lib.ProcessTools
 * @run driver OsCpuLoggingTest
 */

import java.io.File;
import java.util.Map;
import jdk.test.lib.*;

public class OsCpuLoggingTest {

    static void analyzeOutputForOsLog(OutputAnalyzer output) throws Exception {
        // Aix has it's own logging
        if (!Platform.isAix()) {
            output.shouldContain("SafePoint Polling address");
        }
        output.shouldHaveExitValue(0);
    }

    static void analyzeOutputForOsCpuLog(OutputAnalyzer output) throws Exception {
        output.shouldContain("CPU:total");
        output.shouldHaveExitValue(0);
    }

    public static void main(String[] args) throws Exception {

        ProcessBuilder pb = ProcessTools.createJavaProcessBuilder("-Xlog:os+cpu", "-version");
        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        analyzeOutputForOsCpuLog(output);

        pb = ProcessTools.createJavaProcessBuilder("-Xlog:os", "-version");
        output = new OutputAnalyzer(pb.start());
        analyzeOutputForOsLog(output);
    }
}
