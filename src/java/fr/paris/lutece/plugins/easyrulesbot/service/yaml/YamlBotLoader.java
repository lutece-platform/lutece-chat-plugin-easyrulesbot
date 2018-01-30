/*
 * Copyright (c) 2002-2018, Mairie de Paris
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

package fr.paris.lutece.plugins.easyrulesbot.service.yaml;

import fr.paris.lutece.plugins.easyrulesbot.service.yaml.model.YamlFilter;
import fr.paris.lutece.plugins.easyrulesbot.service.yaml.model.YamlBot;
import fr.paris.lutece.plugins.easyrulesbot.service.yaml.model.YamlRule;
import fr.paris.lutece.plugins.easyrulesbot.service.yaml.model.YamlCondition;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import fr.paris.lutece.plugins.chatbot.service.BotService;
import fr.paris.lutece.plugins.easyrulesbot.service.bot.rules.BotRule;
import fr.paris.lutece.plugins.easyrulesbot.service.bot.rules.conditions.Condition;
import fr.paris.lutece.plugins.easyrulesbot.service.bot.rules.conditions.ConditionsService;
import fr.paris.lutece.plugins.easyrulesbot.service.bot.EasyRulesBot;
import fr.paris.lutece.plugins.easyrulesbot.service.response.filters.FiltersService;
import fr.paris.lutece.plugins.easyrulesbot.service.response.filters.ResponseFilter;
import fr.paris.lutece.plugins.easyrulesbot.service.response.processors.ProcessorsService;
import fr.paris.lutece.plugins.easyrulesbot.service.response.processors.ResponseProcessor;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.easyrules.api.RulesEngine;
import org.easyrules.spring.RulesEngineFactoryBean;

/**
 * YamlBotLoader
 */
public class YamlBotLoader
{
    private static final String PATH_BOTS = "/WEB-INF/plugins/easyrulesbot/bots";
    private static RulesEngineFactoryBean _factory;

    public static void loadBots( )
    {
        String strBotPath = AppPathService.getAbsolutePathFromRelativePath( PATH_BOTS );
        File fBotPath = new File( strBotPath );

        File [ ] files = fBotPath.listFiles( new YamlFilenameFilter( ) );
        AppLogService.debug( "Loading YAML bots ... " );
        for ( File file : files )
        {
            try
            {
                String strYaml = FileUtils.readFileToString( file, "UTF-8" );
                YamlBot yBot = loadYamlBot( strYaml );
                EasyRulesBot bot = createBot( yBot );
                BotService.register( bot );
                AppLogService.debug( "New YAML bot registered : " + yBot.getName( ) );
            }
            catch( IOException | YamlBotLoadingException ex )
            {
                AppLogService.error( "Unable to load bot. Error : " + ex.getMessage( ), ex );
            }
        }
    }

    public static YamlBot loadYamlBot( String strYaml ) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper( new YAMLFactory( ) );

        YamlBot bot = mapper.readValue( strYaml, YamlBot.class );

        AppLogService.debug( "Loaded bot : " + bot );
        return bot;
    }

    /**
     * Create a bot from a YAML decription of the bot
     * 
     * @param yamlBot
     *            The YAML decription of the bot
     * @return The bot
     * @throws YamlBotLoadingException
     *             if the loading fails
     */
    public static EasyRulesBot createBot( YamlBot yamlBot ) throws YamlBotLoadingException
    {
        EasyRulesBot bot = new EasyRulesBot( );
        bot.setKey( yamlBot.getKey( ) );
        bot.setName( yamlBot.getName( ) );
        bot.setDescription( yamlBot.getDescription( ) );
        bot.setWelcomeMessage( yamlBot.getWelcomeMessage( ) );
        bot.setAvatarUrl( yamlBot.getAvatarUrl( ) );
        bot.setStandalone( yamlBot.getStandalone( ) );
        List<String> listLanguages = new ArrayList<String>();
        listLanguages.add( yamlBot.getLanguage() );
        bot.setListAvailableLanguages( listLanguages );

        // Get an engine and register rules
        RulesEngine engine = getEngine( );
        for ( YamlRule yamlRule : yamlBot.getRules( ) )
        {
            BotRule rule = new BotRule( );
            rule.setName( yamlRule.getRule( ) );
            rule.setDescription( yamlRule.getDescription( ) );
            rule.setPriority( yamlRule.getPriority( ) );
            rule.setMessageTemplate( yamlRule.getMessage( ) );
            rule.setDataKey( yamlRule.getDataKey( ) );
            ResponseProcessor processor = ProcessorsService.getProcessor( yamlRule.getProcessor( ) );
            if ( processor == null )
            {
                throw new YamlBotLoadingException( "Failed to create bot : unable to find processor " + yamlRule.getProcessor( ) );
            }
            rule.setResponseProcessor( processor );
            rule.setResponseCommentTemplate( yamlRule.getResponseComment( ) );
            List<Condition> listConditions = new ArrayList<>( );
            if( yamlRule.getConditions( ) != null )
            {
                for ( YamlCondition yamlCondition : yamlRule.getConditions( ) )
                {
                    Condition condition = ConditionsService.getCondition( yamlCondition.getCondition( ) );
                    if ( condition == null )
                    {
                        throw new YamlBotLoadingException( "Failed to create bot : unable to find condition " + yamlCondition.getCondition( ) );
                    }
                    condition.setParameters( yamlCondition.getParameters() );
                    listConditions.add( condition );
                }
            }
            rule.setListConditions( listConditions );
            rule.setButtons( yamlRule.getButtons() );
            engine.registerRule( rule );
        }
        bot.setRulesEngine( engine );

        List<ResponseFilter> listFilters = new ArrayList<>( );
        for ( YamlFilter yamlFilter : yamlBot.getFilters( ) )
        {
            ResponseFilter filter = FiltersService.getFilter( yamlFilter.getFilter( ) );
            if ( filter == null )
            {
                throw new YamlBotLoadingException( "Failed to create bot : unable to find filter " + yamlFilter.getFilter( ) );
            }
            listFilters.add( filter );
        }
        bot.setListResponseFilters( listFilters );
        
        AppLogService.debug( "Loaded bot : " + bot );

        return bot;
    }

    private static RulesEngine getEngine( )
    {
        if ( _factory == null )
        {
            _factory = new RulesEngineFactoryBean( );
            _factory.setSkipOnFirstAppliedRule( true );
            _factory.setSkipOnFirstFailedRule( true );
            _factory.setPriorityThreshold( 100 );
            _factory.setSilentMode( false );
        }
        return _factory.getObject( );
    }

    private static class YamlFilenameFilter implements FilenameFilter
    {
        @Override
        public boolean accept( File dir, String filename )
        {
            return filename.endsWith( ".yml" ) || filename.endsWith( ".yaml" );
        }
    }
}
