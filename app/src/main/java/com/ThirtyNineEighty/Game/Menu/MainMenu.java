package com.ThirtyNineEighty.Game.Menu;

import com.ThirtyNineEighty.Base.Common.Math.Vector2;
import com.ThirtyNineEighty.Base.Common.Math.Vector3;
import com.ThirtyNineEighty.Base.Menus.BaseMenu;
import com.ThirtyNineEighty.Base.Menus.Controls.Button;
import com.ThirtyNineEighty.Base.Providers.GLLabelProvider;
import com.ThirtyNineEighty.Base.Renderable.Subprograms.DelayedRenderableSubprogram;
import com.ThirtyNineEighty.Game.TanksContext;
import com.ThirtyNineEighty.Game.Worlds.EditorWorld;
import com.ThirtyNineEighty.Game.Worlds.GameStartArgs;
import com.ThirtyNineEighty.Game.Worlds.GameWorld;
import com.ThirtyNineEighty.Base.Worlds.IWorld;
import com.ThirtyNineEighty.Base.Renderable.GL.GLLabel;
import com.ThirtyNineEighty.Base.Resources.MeshMode;

public class MainMenu
  extends BaseMenu
{
  private GLLabelProvider statsLabel;

  @Override
  public void initialize()
  {
    super.initialize();

    Button continueButton = new Button("Continue");
    continueButton.setPosition(0, 420);
    continueButton.setSize(600, 200);
    continueButton.setClickListener(new Runnable()
    {
      @Override
      public void run()
      {
        IWorld world = TanksContext.content.getWorld();
        if (world == null)
          return;

        if (world instanceof GameWorld)
        {
          world.enable();
          TanksContext.content.setMenu(new GameMenu());
        }
      }
    });
    add(continueButton);

    Button newGameButton = new Button("Solo");
    newGameButton.setPosition(0, 200);
    newGameButton.setSize(600, 200);
    newGameButton.setClickListener(new Runnable()
    {
      @Override
      public void run()
      {
        TanksContext.content.setWorld(null);
        TanksContext.content.setMenu(new MapSelectMenu(new GameStartArgs()));
      }
    });
    add(newGameButton);

    Button statsButton = new Button("Show statistics");
    statsButton.setPosition(0, -20);
    statsButton.setSize(600, 200);
    statsButton.setClickListener(new Runnable()
    {
      @Override
      public void run()
      {
        String resources = TanksContext.resources.getCacheStatus();
        String vec2stats = Vector2.getStatistics();
        String vec3stats = Vector3.getStatistics();

        statsLabel.setValue(String.format("%s\n%s\n%s",resources, vec2stats, vec3stats));

        if (!statsLabel.isVisible())
        {
          statsLabel.setVisible(true);
          bind(new DelayedRenderableSubprogram(statsLabel, 5000));
        }
      }
    });
    add(statsButton);

    statsLabel = new GLLabelProvider(TanksContext.resources.getCacheStatus(), MeshMode.Dynamic);
    statsLabel.setPosition(960, 540);
    statsLabel.setAlign(GLLabelProvider.AlignType.TopRight);
    statsLabel.setVisible(false);
    bind(new GLLabel(statsLabel));

    Button editor = new Button("Editor");
    editor.setPosition(0, -240);
    editor.setSize(600, 200);
    editor.setClickListener(new Runnable()
    {
      @Override
      public void run()
      {
        TanksContext.content.setWorld(new EditorWorld());
        TanksContext.content.setMenu(new EditorMenu());
      }
    });
    add(editor);
  }
}
