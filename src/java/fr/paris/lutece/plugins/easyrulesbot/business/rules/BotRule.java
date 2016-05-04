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
package fr.paris.lutece.plugins.easyrulesbot.business.rules;

import fr.paris.lutece.plugins.easyrulesbot.business.BotExecutor;
import fr.paris.lutece.plugins.easyrulesbot.business.rules.conditions.Condition;
import fr.paris.lutece.plugins.easyrulesbot.service.response.ResponseProcessor;

import org.easyrules.api.Rule;

import java.util.List;
import java.util.Map;


/**
 * BotRule
 */
public class BotRule implements Rule, Comparable
{
    // Variables declarations 
    private String _strName;
    private String _strDescription;
    private String _strDataKey;
    private int _nPriority;
    private String _strQuestion;
    private BotExecutor _executor;
    private ResponseProcessor _responseProcessor;
    private List<Condition> _listConditions;

    /**
     * {@inheritDoc }
     */
    @Override
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
     * {@inheritDoc }
     */
    @Override
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
     * {@inheritDoc }
     */
    @Override
    public int getPriority(  )
    {
        return _nPriority;
    }

    /**
     * Sets the Priority
     * @param nPriority The Priority
     */
    public void setPriority( int nPriority )
    {
        _nPriority = nPriority;
    }

    /**
     * Returns the DataKey
     * @return The DataKey
     */
    public String getDataKey(  )
    {
        return _strDataKey;
    }

    /**
     * Sets the DataKey
     * @param strDataKey The DataKey
     */
    public void setDataKey( String strDataKey )
    {
        _strDataKey = strDataKey;
    }

    /**
     * Returns the Question
     * @return The Question
     */
    public String getQuestion(  )
    {
        return _strQuestion;
    }

    /**
     * Sets the Question
     * @param strQuestion The Question
     */
    public void setQuestion( String strQuestion )
    {
        _strQuestion = strQuestion;
    }

    /**
     *
     * @param executor
     */
    public void setExecutor( BotExecutor executor )
    {
        _executor = executor;
    }

    /**
     * Sets the response processor
     * @param responseProcessor the response processor
     */
    public void setResponseProcessor( ResponseProcessor responseProcessor )
    {
        _responseProcessor = responseProcessor;
    }

    /**
     * Gets the response processor
     * @return the response processor
     */
    public ResponseProcessor getResponseProcessor(  )
    {
        return _responseProcessor;
    }

    /**
     * Sets the conditions list
     * @param listConditions The list of condition
     */
    public void setListConditions( List<Condition> listConditions )
    {
        _listConditions = listConditions;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean evaluate(  )
    {
        Map<String, String> mapData = _executor.getDataMap(  );

        for ( Condition condition : _listConditions )
        {
            if ( !condition.evaluate( mapData, _strDataKey ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void execute(  ) throws Exception
    {
        _executor.setCurrentRule( this );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int compareTo( Object o )
    {
        Rule rule = (Rule) o;

        return getPriority(  ) - rule.getPriority(  );
    }
}
