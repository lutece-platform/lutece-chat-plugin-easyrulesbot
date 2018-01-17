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

package fr.paris.lutece.plugins.easyrulesbot.service.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import fr.paris.lutece.util.html.HtmlTemplate;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ButtonsRenderer
 */
public class ButtonsRenderer implements MessageRenderer
{
    private static final String MESSAGE_TYPE = "buttons";
    private static final String TEMPLATE_BUTTONS_MESSAGE = "/skin/plugins/easyrulesbot/buttons_message.html";

    private static ObjectMapper _mapper = new ObjectMapper( );

    /**
     * {@inheritDoc }
     */
    @Override
    public String getMessageType( )
    {
        return MESSAGE_TYPE;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String render( String strJsonInput )
    {
        String strMessage = null;
        try
        {
            Map<String, Object> model = new HashMap<>( );
            model = _mapper.readValue( strJsonInput, new TypeReference<Map<String, Object>>( )
            {
            } );
            HtmlTemplate message = AppTemplateService.getTemplate( TEMPLATE_BUTTONS_MESSAGE, LocaleService.getDefault( ), model );
            strMessage = message.getHtml( );
        }
        catch( IOException ex )
        {
            AppLogService.error( "Error rendering message : " + ex.getMessage( ), ex );
        }
        return strMessage;
    }

}
