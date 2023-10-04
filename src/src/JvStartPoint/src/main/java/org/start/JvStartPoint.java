package org.start;

import org.auth.JvStartAuthentication;
import org.dbworker.JvDbWorker;

public class JvStartPoint
{
    public static void main( String[] args )
    {
        JvStartAuthentication a = new JvStartAuthentication();
        JvDbWorker db = new JvDbWorker();
    }
}
