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
 * YamlRule
 */
public class YamlRule
{
    // Variables declarations
    private String _strRule;
    private String _strDescription;
    private int _nPriority;
    private String _strMessage;
    private List<Button> _listButtons;
    private String _strImage;
    private String _strDataKey;
    private String _strProcessor;
    private List<YamlCondition> _listConditions;
    private String _strResponseComment;

    /**
     * Returns the Rule Name
     * 
     * @return The Rule Name
     */
    public String getRule( )
    {
        return _strRule;
    }

    /**
     * Sets the Rule Name
     * 
     * @param strRule
     *            The Rule Name
     */
    public void setRule( String strRule )
    {
        _strRule = strRule;
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
     * Returns the Priority
     * 
     * @return The Priority
     */
    public int getPriority( )
    {
        return _nPriority;
    }

    /**
     * Sets the Priority
     * 
     * @param nPriority
     *            The Priority
     */
    public void setPriority( int nPriority )
    {
        _nPriority = nPriority;
    }

    /**
     * Returns the Message
     * 
     * @return The Message
     */
    public String getMessage( )
    {
        return _strMessage;
    }

    /**
     * Sets the Message
     * 
     * @param strMessage
     *            The Message
     */
    public void setMessage( String strMessage )
    {
        _strMessage = strMessage;
    }

    /**
     * Returns the Buttons
     * 
     * @return The Buttons
     */
    public List<Button> getButtons( )
    {
        return _listButtons;
    }

    /**
     * Sets the Buttons
     * 
     * @param listButtons
     *            The Buttons
     */
    public void setButtons( List<Button> listButtons )
    {
        _listButtons = listButtons;
    }

    /**
     * Returns the Image
     * 
     * @return The Image
     */
    public String getImage( )
    {
        return _strImage;
    }

    /**
     * Sets the Image
     * 
     * @param strImage
     *            The Image
     */
    public void setImage( String strImage )
    {
        _strImage = strImage;
    }

    /**
     * Returns the DataKey
     * 
     * @return The DataKey
     */
    public String getDataKey( )
    {
        return _strDataKey;
    }

    /**
     * Sets the DataKey
     * 
     * @param strDataKey
     *            The DataKey
     */
    public void setDataKey( String strDataKey )
    {
        _strDataKey = strDataKey;
    }

    /**
     * Returns the Processor
     * 
     * @return The Processor
     */
    public String getProcessor( )
    {
        return _strProcessor;
    }

    /**
     * Sets the Processor
     * 
     * @param strProcessor
     *            The Processor
     */
    public void setProcessor( String strProcessor )
    {
        _strProcessor = strProcessor;
    }

    /**
     * Returns the Conditions
     * 
     * @return The Conditions
     */
    public List<YamlCondition> getConditions( )
    {
        return _listConditions;
    }

    /**
     * Sets the Conditions
     * 
     * @param listConditions
     *            The Conditions
     */
    public void setConditions( List<YamlCondition> listConditions )
    {
        _listConditions = listConditions;
    }

    /**
     * Returns the ResponseComment
     * 
     * @return The ResponseComment
     */
    public String getResponseComment( )
    {
        return _strResponseComment;
    }

    /**
     * Sets the ResponseComment
     * 
     * @param strResponseComment
     *            The ResponseComment
     */
    public void setResponseComment( String strResponseComment )
    {
        _strResponseComment = strResponseComment;
    }
    
    
}
