# Architecture

## Konzept
- stellt eine DSL zu Verfügung, mit die Architektur von Software beschrieben werden kann
- die DSL bietet dann die Möglichkeit Dateistrkturen des Softwareprojekts zu Komponenten zu mappen
- eine Validierung der DSL gegen den Quellcode zeigt verstöße gegen die, in der DSL beschriebene, Architektur

## Ziel
- die Softwarearchitektur soll als Code beschrieben werden
- die Architketurbeschreibung soll zur Validieurng des Quellcodes dienen
- Architekten und Entwickler können in der IDE die Architekturbeschreibung pflegen

## Technologie
- Bytecodeanalyse
- Classloading
- Domain Specific Language
- Maven/IntelliJ Plugin
