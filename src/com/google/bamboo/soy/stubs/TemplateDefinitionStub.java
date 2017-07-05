package com.google.bamboo.soy.stubs;

import com.google.bamboo.soy.SoyLanguage;
import com.google.bamboo.soy.file.SoyFile;
import com.google.bamboo.soy.parser.SoyTemplateDefinitionIdentifier;
import com.google.bamboo.soy.parser.impl.SoyTemplateDefinitionIdentifierImpl;
import com.google.bamboo.soy.stubs.index.TemplateDefinitionIndex;
import com.intellij.psi.stubs.*;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class TemplateDefinitionStub extends StubBase<SoyTemplateDefinitionIdentifier> {
  static final Type TYPE = new Type();
  public final String name;
  public final String fullyQualifiedName;

  TemplateDefinitionStub(StubElement parent, String name, String fullyQualifiedName) {
    super(parent, TYPE);
    this.name = name;
    this.fullyQualifiedName = fullyQualifiedName;
  }

  static class Type extends IStubElementType<TemplateDefinitionStub, SoyTemplateDefinitionIdentifier> {
    Type() {
      super("TemplateDefinition", SoyLanguage.INSTANCE);
    }

    @Override
    public SoyTemplateDefinitionIdentifier createPsi(@NotNull TemplateDefinitionStub stub) {
      return new SoyTemplateDefinitionIdentifierImpl(stub, this);
    }

    @NotNull
    @Override
    public TemplateDefinitionStub createStub(@NotNull SoyTemplateDefinitionIdentifier psi, StubElement parentStub) {
      String namespace = ((SoyFile)psi.getContainingFile()).getNamespace();
      return new TemplateDefinitionStub(parentStub, psi.getName(), namespace + psi.getName());
    }

    @NotNull
    @Override
    public String getExternalId() {
      return "TemplateDefinition";
    }

    @Override
    public void serialize(@NotNull TemplateDefinitionStub stub, @NotNull StubOutputStream dataStream) throws IOException {
      dataStream.writeName(stub.name);
      dataStream.writeName(stub.fullyQualifiedName);
    }

    @NotNull
    @Override
    public TemplateDefinitionStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
      final StringRef ref = dataStream.readName();
      final StringRef ref2 = dataStream.readName();
      return new TemplateDefinitionStub(parentStub, ref.getString(), ref2.getString());
    }

    @Override
    public void indexStub(@NotNull TemplateDefinitionStub stub, @NotNull IndexSink sink) {
      sink.occurrence(TemplateDefinitionIndex.KEY, stub.fullyQualifiedName);
    }
  }
}
