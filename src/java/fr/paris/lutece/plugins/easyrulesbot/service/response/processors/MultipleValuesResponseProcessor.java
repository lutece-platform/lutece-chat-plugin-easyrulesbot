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
package fr.paris.lutece.plugins.easyrulesbot.service.response.processors;

import fr.paris.lutece.plugins.easyrulesbot.service.response.ResponseProcessor;
import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.ResponseNotUnderstoodException;
import fr.paris.lutece.plugins.easyrulesbot.service.response.exceptions.ResponseProcessingException;
import fr.paris.lutece.portal.service.i18n.I18nService;

import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Multiple Values ResponseProcessor
 */
public class MultipleValuesResponseProcessor implements ResponseProcessor
{
    private Map<String, List<String>> _mapMultipleValues;
    private String _strInvalidResponseMessage;
    private String _strInvalidResponseMessageI18nKey;

    /**
     * Set the map of values / terms
     * @param map the map
     */
    public void setValueTermsMap( Map<String, List<String>> map )
    {
        _mapMultipleValues = map;
    }

    /**
     * Set the Invalid Response Message
     * @param strMessage The message
     */
    public void setInvalidResponseMessage( String strMessage )
    {
        _strInvalidResponseMessage = strMessage;
    }

    /**
     * Set the Invalid Response Message
     * @param strMessage The message
     */
    public void setInvalidResponseMessageI18nKey( String strMessage )
    {
        _strInvalidResponseMessageI18nKey = strMessage;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String processResponse( String strResponse, Locale locale, Map mapData )
        throws ResponseProcessingException
    {
        String strResponseToCheck = strResponse.toLowerCase(  );

        for ( String strValue : _mapMultipleValues.keySet(  ) )
        {
            List<String> listTerms = _mapMultipleValues.get( strValue );

            for ( String strTerm : listTerms )
            {
                if ( strResponseToCheck.contains( strTerm ) )
                {
                    return strValue;
                }
            }
        }

        throw new ResponseNotUnderstoodException( getInvalidResponse( locale ) );
    }

    /**
     * Returns invalid response message
     * @param locale The locale
     * @return The message
     */
    private String getInvalidResponse( Locale locale )
    {
        String strResponse;

        if ( _strInvalidResponseMessageI18nKey != null )
        {
            strResponse = I18nService.getLocalizedString( _strInvalidResponseMessageI18nKey, locale );
        }
        else
        {
            strResponse = _strInvalidResponseMessage;
        }

        return strResponse;
    }
}
