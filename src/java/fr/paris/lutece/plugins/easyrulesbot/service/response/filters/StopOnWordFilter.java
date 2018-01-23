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
package fr.paris.lutece.plugins.easyrulesbot.service.response.filters;

import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.ResponseProcessingException;
import fr.paris.lutece.plugins.easyrulesbot.util.FileUtils;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Stop On Word Processor
 */
public class StopOnWordFilter extends AbstractFilter implements ResponseFilter
{

    private List<String> _listStopWords;
    private Map<String, String> _mapLocaleStopWordsFile;
    private Map<String, List<String>> _mapLocaleStopWordsList = new HashMap<>( );
    private Map<String, String> _mapLocaleResponseMessageFile;
    private Map<String, List<String>> _mapLocaleResponseMessageList = new HashMap<>( );

    /**
     * Get the list of stop words
     *
     * @return The list
     */
    public List<String> getListStopWords( )
    {
        return _listStopWords;
    }

    /**
     * Set Map Locale / Stop Words File
     *
     * @param map
     *            The map
     */
    public void setMapLocaleStopWordsFile( Map<String, String> map )
    {
        _mapLocaleStopWordsFile = map;
    }

    /**
     * Set the list of stop words
     *
     * @param list
     *            The list
     */
    public void setListStopWords( List<String> list )
    {
        _listStopWords = list;
    }

    /**
     * Set Map Locale / Messages File
     *
     * @param map
     *            The map
     */
    public void setMapLocaleResponseMessageFile( Map<String, String> map )
    {
        _mapLocaleResponseMessageFile = map;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String filterResponse( String strResponse, Locale locale, Map mapData ) throws ResponseProcessingException
    {
        String strCheck = strResponse.toLowerCase( );

        for ( String strWord : getWords( locale ) )
        {
            if ( strCheck.contains( strWord ) )
            {
                throw new ResponseProcessingException( getStopResponseMessage( locale ) );
            }
        }

        return strResponse;
    }

    /**
     * Gets the stop message list
     *
     * @param locale
     *            The locale
     * @return A random message on the list
     */
    private String getStopResponseMessage( Locale locale )
    {
        String strMessage;

        if ( locale == null )
        {
            AppLogService.error( "Locale is NULL" );
            locale = LocaleService.getDefault( ); // FIXME
        }

        String strLanguage = locale.getLanguage( );
        List<String> listMessages = _mapLocaleResponseMessageList.get( strLanguage );
        if ( listMessages == null )
        {
            String strMessageFile = _mapLocaleResponseMessageFile.get( strLanguage );
            if ( strMessageFile != null )
            {
                listMessages = FileUtils.loadTermsFromFile( strMessageFile );

                _mapLocaleResponseMessageList.put( strLanguage, listMessages );
            }
            else
            {
                AppLogService.error( "EasyRuleBot : StopOnWordFilter : No Response Message file available for the language : " + strLanguage );
                return "";
            }
        }
        Random randomizer = new Random( );
        strMessage = listMessages.get( randomizer.nextInt( listMessages.size( ) ) );

        return strMessage;
    }

    /**
     * Gets the list of words
     *
     * @return The list
     */
    private List<String> getWords( Locale locale )
    {
        String strLanguage = locale.getLanguage( );
        List<String> listStopWords = _mapLocaleStopWordsList.get( strLanguage );
        if ( listStopWords == null )
        {
            listStopWords = new ArrayList<String>( );
            if ( _mapLocaleStopWordsFile != null )
            {
                String strMessageFile = _mapLocaleStopWordsFile.get( strLanguage );
                if ( strMessageFile != null )
                {
                    listStopWords.addAll( FileUtils.loadTermsFromFile( strMessageFile ) );
                }
            }
            if ( _listStopWords != null )
            {
                listStopWords.addAll( _listStopWords );
            }
            _mapLocaleStopWordsList.put( strLanguage, listStopWords );
        }

        return listStopWords;
    }
}
