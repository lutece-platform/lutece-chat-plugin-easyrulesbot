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
package fr.paris.lutece.plugins.easyrulesbot.business;

import fr.paris.lutece.plugins.easyrulesbot.service.response.ResponseFilter;
import fr.paris.lutece.portal.service.i18n.I18nService;

import org.easyrules.api.RulesEngine;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * Bot
 */
public class Bot implements Serializable
{
    private static final String PROPERTY_LAST_MESSAGE = "easyrulesbot.bot.lastMessage";
    private static final long serialVersionUID = 1L;
    private String _strKey;
    private String _strName;
    private String _strDescription;
    private String _strNameI18nKey;
    private String _strDescriptionI18nKey;
    private RulesEngine _engine;
    private List<ResponseFilter> _listResponseFilters;
    private List<String> _listAvailableLanguages;

    /**
     * Returns the Key
     * @return The Key
     */
    public String getKey(  )
    {
        return _strKey;
    }

    /**
     * Sets the Key
     * @param strKey The Key
     */
    public void setKey( String strKey )
    {
        _strKey = strKey;
    }

    /**
     * Returns the Name
     * @param locale The locale
     * @return The Name
     */
    public String getName( Locale locale )
    {
        String strName;

        if ( _strNameI18nKey != null )
        {
            strName = I18nService.getLocalizedString( _strNameI18nKey, locale );
        }
        else
        {
            strName = _strName;
        }

        return strName;
    }

    /**
     * Sets the Name
     * @param strName The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns the Description
     * @param locale The locale
     * @return The Description
     */
    public String getDescription( Locale locale )
    {
        String strDescription;

        if ( _strDescriptionI18nKey != null )
        {
            strDescription = I18nService.getLocalizedString( _strDescriptionI18nKey, locale );
        }
        else
        {
            strDescription = _strDescription;
        }

        return strDescription;
    }

    /**
     * Sets the Description
     * @param strDescription The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the NameI18nKey
     * @return The NameI18nKey
     */
    public String getNameI18nKey(  )
    {
        return _strNameI18nKey;
    }

    /**
     * Sets the NameI18nKey
     * @param strNameI18nKey The NameI18nKey
     */
    public void setNameI18nKey( String strNameI18nKey )
    {
        _strNameI18nKey = strNameI18nKey;
    }

    /**
     * Returns the DescriptionI18nKey
     * @return The DescriptionI18nKey
     */
    public String getDescriptionI18nKey(  )
    {
        return _strDescriptionI18nKey;
    }

    /**
     * Sets the DescriptionI18nKey
     * @param strDescriptionI18nKey The DescriptionI18nKey
     */
    public void setDescriptionI18nKey( String strDescriptionI18nKey )
    {
        _strDescriptionI18nKey = strDescriptionI18nKey;
    }

    /**
     * Returns the RulesEngine
     * @return The RulesEngine
     */
    public RulesEngine getRulesEngine(  )
    {
        return _engine;
    }

    /**
     * Sets the RulesEngine
     * @param rulesEngine The RulesEngine
     */
    public void setRulesEngine( RulesEngine rulesEngine )
    {
        _engine = rulesEngine;
    }

    /**
     * Set the list of response filters
     * @param list The list
     *
     */
    public void setListResponseFilters( List<ResponseFilter> list )
    {
        _listResponseFilters = list;
    }

    /**
     * The list of response filters
     * @return The list
     */
    public List<ResponseFilter> getResponseFilters(  )
    {
        return _listResponseFilters;
    }

    /**
     * Set available languages list
     * @param listAvailableLanguages available languages list
     */
    public void setListAvailableLanguages( List<String> listAvailableLanguages )
    {
        _listAvailableLanguages = listAvailableLanguages;
    }

    /**
     * Return available languages list
     * @return available languages list
     */
    public List<String> getAvailableLanguages(  )
    {
        return _listAvailableLanguages;
    }

    /**
     * Last bot post build with data collected.
     * Default implementation. Should be override
     * @param request The HTTP request
     * @param mapData The data
     * @param locale The locale
     * @return The last message
     */
    public String processData( HttpServletRequest request, Map<String, String> mapData, Locale locale )
    {
        String strLastMessage = I18nService.getLocalizedString( PROPERTY_LAST_MESSAGE, locale );
        StringBuilder sbLastMessage = new StringBuilder( strLastMessage );
        sbLastMessage.append( "<ul>" );

        for ( String strKey : mapData.keySet(  ) )
        {
            sbLastMessage.append( "<li>" ).append( strKey ).append( " : " ).append( mapData.get( strKey ) )
                         .append( "</li>" );
        }

        sbLastMessage.append( "</ul>" );

        return sbLastMessage.toString(  );
    }
}
