package com.fanwe.lib.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FLogFormatter extends Formatter
{
    private final Date mDate = new Date();
    private final SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");
    private StringBuilder mStringBuilder = new StringBuilder();

    private void clearStringBuilder()
    {
        if (mStringBuilder.length() > 0)
        {
            mStringBuilder.delete(0, mStringBuilder.length());
        }
    }

    @Override
    public String format(LogRecord record)
    {
        clearStringBuilder();

        mDate.setTime(record.getMillis());
        String date = mDateFormat.format(mDate);

        String message = formatMessage(record);

        String throwable = "";
        if (record.getThrown() != null)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }

        mStringBuilder.append(date).append("(").append(record.getLevel()).append(") ").append(message);
        mStringBuilder.append(throwable);

        mStringBuilder.append(getNextLine());
        return mStringBuilder.toString();
    }

    private static String getNextLine()
    {
        return System.getProperty("line.separator");
    }
}