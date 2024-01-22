import dev.funkymuse.kahelpers.getBundle
import dev.funkymuse.kahelpers.getLibrary
import dev.funkymuse.kahelpers.implementation
import dev.funkymuse.kahelpers.ksp
import dev.funkymuse.kahelpers.kspPluginId
import dev.funkymuse.kahelpers.versionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = versionCatalog
            pluginManager.apply(versionCatalog.kspPluginId)
            dependencies {
                add(implementation, libs.getBundle("room"))
                add(ksp, libs.getLibrary("androidx-room-compiler"))
            }
        }
    }
}