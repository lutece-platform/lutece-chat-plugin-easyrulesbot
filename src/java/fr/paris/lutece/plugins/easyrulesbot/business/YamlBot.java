/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import java.util.List;

/**
 * YamlBot
 */
public class YamlBot
{
    // Variables declarations
    private String _strKey;
    private String _strName;
    private String _strDescription;
    private String _strLanguage;
    private String _strWelcomeMessage;
    private String _strAvatarUrl;
    private List<YamlRule> _listRules;
    private List<YamlFilter> _listFilters;
    private boolean _bStandalone;

    /**
     * Returns the Key
     *
     * @return The Key
     */
    public String getKey( )
    {
        return _strKey;
    }

    /**
     * Sets the Key
     *
     * @param strKey
     *            The Key
     */
    public void setKey( String strKey )
    {
        _strKey = strKey;
    }

    /**
     * Returns the Name
     *
     * @return The Name
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * Sets the Name
     *
     * @param strName
     *            The Name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Returns the Description
     *
     * @return The Description
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     *
     * @param strDescription
     *            The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the Language
     *
     * @return The Language
     */
    public String getLanguage( )
    {
        return _strLanguage;
    }

    /**
     * Sets the Language
     *
     * @param strLanguage
     *            The Language
     */
    public void setLanguage( String strLanguage )
    {
        _strLanguage = strLanguage;
    }

    /**
     * Returns the WelcomeMessage
     *
     * @return The WelcomeMessage
     */
    public String getWelcomeMessage( )
    {
        return _strWelcomeMessage;
    }

    /**
     * Sets the WelcomeMessage
     *
     * @param strWelcomeMessage
     *            The WelcomeMessage
     */
    public void setWelcomeMessage( String strWelcomeMessage )
    {
        _strWelcomeMessage = strWelcomeMessage;
    }

    /**
     * Returns the AvatarUrl
     *
     * @return The AvatarUrl
     */
    public String getAvatarUrl( )
    {
        return _strAvatarUrl;
    }

    /**
     * Sets the AvatarUrl
     *
     * @param strAvatarUrl
     *            The AvatarUrl
     */
    public void setAvatarUrl( String strAvatarUrl )
    {
        _strAvatarUrl = strAvatarUrl;
    }

    /**
     * Returns the Standalone
     *
     * @return The Standalone
     */
    public boolean getStandalone( )
    {
        return _bStandalone;
    }

    /**
     * Sets the Standalone
     *
     * @param bStandalone
     *            The Standalone
     */
    public void setStandalone( boolean bStandalone )
    {
        _bStandalone = bStandalone;
    }

    /**
     * Returns the Rules
     *
     * @return The Rules
     */
    public List<YamlRule> getRules( )
    {
        return _listRules;
    }

    /**
     * Sets the Rules
     *
     * @param listRules
     *            The Rules
     */
    public void setRules( List<YamlRule> listRules )
    {
        _listRules = listRules;
    }

    /**
     * Returns the Filters
     *
     * @return The Filters
     */
    public List<YamlFilter> getFilters( )
    {
        return _listFilters;
    }

    /**
     * Sets the Filters
     *
     * @param listFilters
     *            The Filters
     */
    public void setFilters( List<YamlFilter> listFilters )
    {
        _listFilters = listFilters;
    }

}
