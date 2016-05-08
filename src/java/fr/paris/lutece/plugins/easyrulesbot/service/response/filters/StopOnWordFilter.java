/*
 * Copyright (c) 2002-2015, Mairie de Paris
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

import fr.paris.lutece.plugins.easyrulesbot.service.response.ResponseFilter;
import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.ResponseProcessingException;
import fr.paris.lutece.portal.service.i18n.I18nService;

import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Stop On Word Processor
 */
public class StopOnWordFilter implements ResponseFilter
{
    private List<String> _listStopWords;
    private String _strStopMessage;
    private String _strStopMessageI18nKey;

    /**
     * Set the list of stop words
     * @param list The list
     */
    public void setListStopWords( List<String> list )
    {
        _listStopWords = list;
    }

    /**
     * Set the stop message
     * @param strMessage The message
     */
    public void setMessage( String strMessage )
    {
        _strStopMessage = strMessage;
    }

    /**
     * Set the stop message
     * @param strMessage The message
     */
    public void setMessageI18nKey( String strMessage )
    {
        _strStopMessageI18nKey = strMessage;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String filterResponse( String strResponse, Locale locale, Map mapData )
        throws ResponseProcessingException
    {
        String strCheck = strResponse.toLowerCase(  );

        for ( String strWord : _listStopWords )
        {
            if ( strCheck.contains( strWord ) )
            {
                throw new ResponseProcessingException( getStopMessage( locale ) );
            }
        }

        return strResponse;
    }

    /**
     * Gets the stop message
     * @param locale The locale
     * @return The message
     */
    private String getStopMessage( Locale locale )
    {
        String strMessage;

        if ( _strStopMessageI18nKey != null )
        {
            strMessage = I18nService.getLocalizedString( _strStopMessageI18nKey, locale );
        }
        else
        {
            strMessage = _strStopMessage;
        }

        return strMessage;
    }
}
