/*
 * Copyright (c) 2002-2016, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.easyrulesbot.util;

import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileUtils
 */
public class FileUtils
{

    public static final Pattern PATTERN_GROUP_KEY = Pattern.compile( "\\[(.*?)\\]" );

    /**
     * Load Terms from a file. Each term is separated by a comma or new line
     *
     * @param strRelativeFilePath
     *            The file path
     * @return The list of terms
     */
    public static List<String> loadTermsFromFile( String strRelativeFilePath )
    {
        List<String> list = new ArrayList<String>( );

        try
        {
            BufferedReader br = null;
            try
            {
                String strFilePath = AppPathService.getAbsolutePathFromRelativePath( strRelativeFilePath );
                br = new BufferedReader( new FileReader( strFilePath ) );

                String strLine = br.readLine( );

                while ( strLine != null )
                {
                    list.add( strLine.trim( ) );
                    strLine = br.readLine( );
                }
            }
            finally
            {
                if ( br != null )
                {
                    br.close( );
                }
            }
        }
        catch( IOException ex )
        {
            AppLogService.error( "Error loading list of terms file : " + ex.getMessage( ), ex );
        }

        return list;
    }

    /**
     * Load map of terms from a file. keys are within brackets and terms are following on new lines each
     *
     * @param strRelativeFilePath
     *            The file path
     * @return The map of terms
     */
    public static Map<String, List<String>> loadMapFromFile( String strRelativeFilePath )
    {
        Map<String, List<String>> map = null;
        String strFilePath = AppPathService.getAbsolutePathFromRelativePath( strRelativeFilePath );
        try
        {
            map = FileUtils.loadMapFromFile( new FileInputStream( strFilePath ) );
        }
        catch( FileNotFoundException ex )
        {
            AppLogService.error( "Error loading map of terms file : " + ex.getMessage( ), ex );
        }
        return map;

    }

    public static Map<String, List<String>> loadMapFromFile( InputStream is )
    {
        Map<String, List<String>> map = new HashMap<>( );

        try
        {
            BufferedReader br = null;
            try
            {
                br = new BufferedReader( new InputStreamReader( is, "UTF-8" ) );

                String strLine = br.readLine( );

                String strCurrentKey = null;
                while ( strLine != null )
                {
                    if ( strLine.trim( ).length( ) == 0 )
                    {
                        strLine = br.readLine( );
                        continue; // Skip blank lines
                    }

                    Matcher m = PATTERN_GROUP_KEY.matcher( strLine );

                    if ( m.find( ) )
                    {
                        strCurrentKey = m.group( 1 );
                        map.put( strCurrentKey, new ArrayList<String>( ) );
                    }
                    else
                        if ( strCurrentKey != null )
                        {
                            List<String> list = map.get( strCurrentKey );
                            list.add( strLine.trim( ) );
                        }
                    strLine = br.readLine( );
                }
            }
            finally
            {
                if ( br != null )
                {
                    br.close( );
                }
            }
        }
        catch( IOException ex )
        {
            AppLogService.error( "Error loading list of terms file : " + ex.getMessage( ), ex );
        }
        return map;
    }

}
