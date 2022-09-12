package com.miaow.ticket.core.util;

import com.miaow.ticket.core.exception.TicketException;
import com.miaow.ticket.core.exception.TicketExceptionEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class FileUtils {

    public static byte[] toByteArray(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            log.error("文件未找到！" + filename);
            throw new TicketException(TicketExceptionEnum.FILE_NOT_FOUND);
        }

        FileChannel channel = null;
        FileInputStream fs = null;

        try {
            fs = new FileInputStream(file);
            channel = fs.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do thing
            }
            return byteBuffer.array();
        } catch (IOException e) {
            log.error("文件读取异常！", e);
            throw new TicketException(TicketExceptionEnum.FILE_READING_ERROR);
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                log.error("转换文件流关闭异常！", e);
                throw new TicketException(TicketExceptionEnum.FILE_READING_ERROR);
            }
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                log.error("转换文件流关闭异常！", e);
                throw new TicketException(TicketExceptionEnum.FILE_READING_ERROR);
            }
        }
    }

}
