import java.io.*;            // InputStream�AByteArrayOutputStream�ɕK�v

public class Framer {

  public static byte[] nextToken(InputStream in, byte[] delimiter)
       throws IOException {
    int nextByte;

    // �X�g���[�������łɏI�����Ă���ꍇ��null��Ԃ�
    if ((nextByte = in.read()) == -1)
      return null;

    ByteArrayOutputStream tokenBuffer = new ByteArrayOutputStream();
    do {
      tokenBuffer.write(nextByte);
      byte[] currentToken = tokenBuffer.toByteArray();
      if (endsWith(currentToken, delimiter)) {
        int tokenLength = currentToken.length - delimiter.length;
        byte[] token = new byte[tokenLength];
        System.arraycopy(currentToken, 0, token, 0, tokenLength);
        return token;
      }
    } while ((nextByte = in.read()) != -1);   // �X�g���[���̏I���Œ�~
    return tokenBuffer.toByteArray();         // ���Ȃ��Ƃ�1�o�C�g����M����
  }

  // value��suffix�Ɋi�[���ꂽ�o�C�g��ŏI�����Ă���ꍇ��true��Ԃ�
  private static boolean endsWith(byte[] value, byte[] suffix) {
    if (value.length < suffix.length)
      return false;

    for (int offset = 1; offset <= suffix.length; offset++)
      if (value[value.length - offset] != suffix[suffix.length - offset])
        return false;
    return true;
  }
}
