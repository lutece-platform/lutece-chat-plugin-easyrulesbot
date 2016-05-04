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

import org.easyrules.api.RulesEngine;

import java.util.List;


/**
 * Bot
 */
public class Bot
{
    private String _strName;
    private String _strDescription;
    private RulesEngine _engine;
    private List<ResponseFilter> _listResponseFilters;

    /**
     * Returns the Name
     * @return The Name
     */
    public String getName(  )
    {
        return _strName;
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
     * @return The Description
     */
    public String getDescription(  )
    {
        return _strDescription;
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
     * Returns the RulesEngine
     * @return The RulesEngine
     */
    public RulesEngine getRulesEngine(  )
    {
        return _engine;
    }

    /**
     * Sets the RulesEngine
     * @param RulesEngine The RulesEngine
     */
    public void setRulesEngine( RulesEngine RulesEngine )
    {
        _engine = RulesEngine;
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
    List<ResponseFilter> getResponseFilters(  )
    {
        return _listResponseFilters;
    }
}
