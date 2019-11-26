package module_micro_api_qrcode.api;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.github.microprograms.micro_api_runtime.annotations.Comment;
import com.github.microprograms.micro_api_runtime.annotations.MicroApi;
import com.github.microprograms.micro_api_runtime.annotations.Required;
import com.github.microprograms.micro_api_runtime.model.Api;
import com.github.microprograms.micro_api_runtime.model.Request;
import com.github.microprograms.micro_api_runtime.model.Response;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import eu.maxschuster.dataurl.DataUrl;
import eu.maxschuster.dataurl.DataUrlBuilder;
import eu.maxschuster.dataurl.DataUrlEncoding;
import eu.maxschuster.dataurl.DataUrlSerializer;
import java.util.List;

@MicroApi(name = "createQrcodeAsDataUrl", version = "v1.0.0")
@Comment("创建二维码，返回DataUrl编码的文本格式")
public class CreateQrcodeAsDataUrl implements Api {

    @Override
    public String execute(String request) throws Exception {
        Req req = JSON.parseObject(request, Req.class);
        Resp resp = new Resp();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, req.getMargin());
        BitMatrix bitMatrix = new MultiFormatWriter().encode(req.getText(), BarcodeFormat.QR_CODE, req.getWidth(), req.getHeight(), hints);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(bitMatrix, req.getFormat(), byteArrayOutputStream);
            DataUrl dataUrl = new DataUrlBuilder().setMimeType(req.getMimeType()).setEncoding(DataUrlEncoding.BASE64).setData(byteArrayOutputStream.toByteArray()).build();
            resp.setDataUrl(new DataUrlSerializer().serialize(dataUrl));
        }
        return JSON.toJSONString(resp);
    }

    public static class Req extends Request {

        @Comment("文本内容")
        @Required
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Comment("图片宽度")
        @Required
        private Integer width;

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        @Comment("图片高度")
        @Required
        private Integer height;

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        @Comment("边距")
        @Required
        private Integer margin;

        public Integer getMargin() {
            return margin;
        }

        public void setMargin(Integer margin) {
            this.margin = margin;
        }

        @Comment("图片格式")
        @Required
        private String format;

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        @Comment("图片MIME-Type")
        @Required
        private String mimeType;

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }
    }

    public static class Resp extends Response {

        @Comment("data url")
        @Required
        private String dataUrl;

        public String getDataUrl() {
            return dataUrl;
        }

        public void setDataUrl(String dataUrl) {
            this.dataUrl = dataUrl;
        }
    }
}
