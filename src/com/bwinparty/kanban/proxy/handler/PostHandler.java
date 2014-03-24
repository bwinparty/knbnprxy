/*
 * knbnprxy - Kanban Proxy
 *
 * Copyright 2014   bwin.party digital entertainment plc
 *                  http://www.bwinparty.com
 * Developer: Lukas Prettenthaler
 */
package com.bwinparty.kanban.proxy.handler;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;

/**
 * Created by zyclonite on 12/03/14.
 */
public class PostHandler implements Handler<HttpServerRequest> {
    private static final Logger LOG = LoggerFactory.getLogger(PostHandler.class);

    @Override
    public void handle(final HttpServerRequest httpServerRequest) {
        LOG.trace("handling request");
        final String endpoint = httpServerRequest.params().get("endpoint");
        httpServerRequest.response().putHeader("Access-Control-Allow-Origin", "*");
        httpServerRequest.response().putHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        httpServerRequest.response().putHeader("Pragma", "no-cache");
        httpServerRequest.bodyHandler(new Handler<Buffer>() {
            @Override
            public void handle(final Buffer buffer) {
                SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                String dateString = dformat.format( System.currentTimeMillis() );
                Buffer output;
                String contenttype = "application/json; charset=utf-8";
                String fileName = "kanban_transcoded_"+httpServerRequest.params().contains("context")+"_"+dateString;
                try {
                    switch (endpoint) {
                        case "compile":
                            if (!httpServerRequest.params().contains("format")) {
                                throw new Exception("no format parameter");
                            }
                            if (!httpServerRequest.params().contains("context")) {
                                throw new Exception("no context parameter");
                            }
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            TranscoderInput transcoderInput;
                            TranscoderOutput transcoderOutput;
                            Transcoder transcoder;
                            switch(httpServerRequest.params().get("format")) {
                                case "pdf":
                                    fileName+=".pdf";
                                    transcoderInput = new TranscoderInput(new ByteArrayInputStream(buffer.getBytes()));
                                    transcoderOutput = new TranscoderOutput(outputStream);
                                    transcoder = new PDFTranscoder();
                                    transcoder.transcode(transcoderInput, transcoderOutput);
                                    contenttype = "application/pdf";
                                    output = new Buffer(outputStream.toByteArray());
                                    break;
                                case "png":
                                    fileName+=".png";
                                    if (!httpServerRequest.params().contains("width") || !httpServerRequest.params().contains("height") || !httpServerRequest.params().contains("scale")) {
                                        throw new Exception("missing width, height or scale parameter for png");
                                    }
                                    float width = new Float(httpServerRequest.params().get("width")) * new Float(httpServerRequest.params().get("scale"));
                                    float height = new Float(httpServerRequest.params().get("height")) * new Float(httpServerRequest.params().get("scale"));
                                    transcoderInput = new TranscoderInput(new StringReader(buffer.toString()));
                                    transcoderOutput = new TranscoderOutput(outputStream);
                                    transcoder = new PNGTranscoder();
                                    //transcoder.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE,Boolean.TRUE);
                                    transcoder.addTranscodingHint(PNGTranscoder.KEY_BACKGROUND_COLOR, Color.white);
                                    transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
                                    transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);
                                    transcoder.transcode(transcoderInput, transcoderOutput);
                                    contenttype = "image/png";
                                    output = new Buffer(outputStream.toByteArray());
                                    break;
                                case "svg":
                                    fileName+=".svg";
                                    contenttype = "image/svg+xml";
                                    output = buffer.copy();
                                    break;
                                default:
                                    throw new Exception("wrong format");
                            }
                            break;
                        default:
                            throw new Exception("wrong endpoint");
                    }
                    httpServerRequest.response().setStatusCode(200);
                    httpServerRequest.response().putHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                } catch (Exception ex) {
                    httpServerRequest.response().setStatusCode(500);
                    output = new Buffer("{\"error\":\"server error\",\"exception\":\""+ex.getMessage()+"\"}");
                    LOG.warn(ex.getMessage(), ex);
                }
                httpServerRequest.response().putHeader("Content-Type", contenttype);
                httpServerRequest.response().end(output);
            }
        });
    }
}
